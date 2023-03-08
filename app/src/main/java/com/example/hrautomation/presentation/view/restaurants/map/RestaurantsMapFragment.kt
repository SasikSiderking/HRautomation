package com.example.hrautomation.presentation.view.restaurants.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentMapBinding
import com.example.hrautomation.presentation.model.restaurants.BuildingItem
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.view.restaurants.RestaurantsViewModel
import com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet.RestaurantBottomSheet
import com.example.hrautomation.presentation.view.restaurants.сity_bottom_sheet.CityBottomSheet
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


class RestaurantsMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding!!

    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var mapAdapter: MapAdapter

    private var chosenCityLatLng: LatLng? = null

    private lateinit var cityFragment: CityBottomSheet
    private lateinit var restaurantFragment: RestaurantBottomSheet

    private lateinit var restaurantCardAdapter: UpdatableViewAdapter<ListRestaurantItem, RestaurantCard>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RestaurantsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        initToolbar()
        initListeners()
        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onMapReady(map: GoogleMap) {
        mapAdapter = MapAdapter(map)

        viewModel.data.observe(viewLifecycleOwner, buildingsObserver)
        viewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)
    }

    private val markerClickListener: OnMarkerDelegateClickListener =
        OnMarkerDelegateClickListener { markerDelegate: MarkerDelegate ->
            viewModel.chooseBuilding(markerDelegate.marker?.tag as Long, markerDelegate)
        }

    private val onCardClickListener = OnCardClickListener { cardAction ->
        when (cardAction) {
            CardAction.CLOSE -> {
                viewModel.resetChosenBuilding()
            }
            CardAction.GO_TO -> {
//                TODO(Открыть активити с фулл рестораном)
            }
        }
    }

    private val chooseCityClickListener = OnClickListener {
        cityFragment = CityBottomSheet.newInstance()
        cityFragment.show(childFragmentManager, CityBottomSheet.TAG)
    }

    private val stateObserver = Observer<RestaurantsMapState> { newState ->
        if (chosenCityLatLng != newState.chosenCityLatLng) {
            chosenCityLatLng = newState.chosenCityLatLng
            mapAdapter.moveCamera(chosenCityLatLng!!, MAP_ZOOM)
        }

        if (newState.chosenMarker != null) {
            mapAdapter.chooseMarker(newState.chosenMarker.id)
        } else {
            mapAdapter.unChooseMarker()
        }

        with(newState) {
            if (chosenBuildingId != null) {
                val chosenRestaurantId = viewModel.singleRestaurantIdInBuildingOrNull(chosenBuildingId)
                if (chosenRestaurantId != null) {
                    restaurantCardAdapter.updateView(chosenRestaurantId)
                } else {
                    restaurantCardAdapter.closeView()
                    openRestaurantsBottomSheet(chosenBuildingId)
                }
            } else {
                restaurantCardAdapter.closeView()
            }
        }
    }

    private fun openRestaurantsBottomSheet(buildingId: Long) {
        restaurantFragment = RestaurantBottomSheet.newInstance(buildingId)

        restaurantFragment.show(childFragmentManager, RestaurantBottomSheet.TAG)
    }

    private val buildingsObserver = Observer<List<BuildingItem>> { buildings ->
        mapAdapter.setMarkers(buildings, requireContext())

        mapAdapter.setMarkerClickListener(markerClickListener)

        viewModel.restaurantsMapState.observe(viewLifecycleOwner, stateObserver)
    }

    private val restaurantsObserver = Observer<List<ListRestaurantItem>> { restaurants ->
        restaurantCardAdapter.setItems(restaurants)
    }

    private fun initUi() {
        restaurantCardAdapter = UpdatableViewAdapter(binding.restaurantCard)

        supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.elevation = requireContext().dpToPx(TOOLBAR_ELEVATION).toFloat()
        }
    }

    private fun initListeners() {
        binding.restaurantCard.setCardClickListener(onCardClickListener)
        binding.chooseCityButton.setOnClickListener(chooseCityClickListener)

        childFragmentManager.setFragmentResultListener(
            CityBottomSheet.TAG,
            viewLifecycleOwner
        ) { _: String, bundle: Bundle ->
            val lat = bundle.getDouble(CityBottomSheet.LATITUDE_KEY)
            val lng = bundle.getDouble(CityBottomSheet.LONGITUDE_KEY)

            viewModel.chooseCity(LatLng(lat, lng))
        }

        childFragmentManager.setFragmentResultListener(
            RestaurantBottomSheet.TAG,
            viewLifecycleOwner
        ) { _: String, _: Bundle ->
            viewModel.resetChosenBuilding()
        }
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F

        const val MAP_ZOOM = 12F
    }
}