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
import com.example.hrautomation.presentation.view.restaurants.RestaurantsViewModel
import com.example.hrautomation.presentation.view.restaurants.сity.CitiesListFragment
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

    private lateinit var cityFragment: CitiesListFragment

    private lateinit var restaurantCardAdapter: UpdatableViewAdapter<BuildingItem, RestaurantCard>

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

        viewModel.restaurantsMapState.observe(viewLifecycleOwner, stateObserver)
        viewModel.data.observe(viewLifecycleOwner, buildingsObserver)
    }

    private val markerClickListener: OnMarkerDelegateClickListener =
        OnMarkerDelegateClickListener { markerDelegate: MarkerDelegate ->
            viewModel.chooseRestaurant(markerDelegate.marker?.tag as Long, markerDelegate)
        }

    private val onCardClickListener = OnCardClickListener { cardAction ->
        when (cardAction) {
            CardAction.CLOSE -> {
                viewModel.resetChosenRestaurant()
            }
            CardAction.GO_TO -> {
//                TODO(Открыть активити с фулл рестораном)
            }
        }
    }

    private val chooseCityClickListener = OnClickListener {
        cityFragment = CitiesListFragment.newInstance()
        cityFragment.show(childFragmentManager, CitiesListFragment.TAG)
    }

    private val stateObserver = Observer<RestaurantsMapState> { newState ->
        mapAdapter.moveCamera(newState.chosenCityLatLng, MAP_ZOOM)

        if (newState.chosenMarker != null) {
            mapAdapter.chooseMarker(newState.chosenMarker.id)
        } else {
            mapAdapter.unChooseMarker()
        }

        with(newState) {
            if (chosenBuildingId != null) {
                restaurantCardAdapter.updateView(chosenBuildingId)
            } else {
                restaurantCardAdapter.closeView()
            }
        }
    }

    private val buildingsObserver = Observer<List<BuildingItem>> { buildings ->
        restaurantCardAdapter.setItems(buildings)
        mapAdapter.setMarkers(buildings, requireContext())

        mapAdapter.setMarkerClickListener(markerClickListener)

        viewModel.restaurantsMapState.observe(viewLifecycleOwner, stateObserver)
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
            CitiesListFragment.CITIES_FRAGMENT_KEY,
            viewLifecycleOwner
        ) { _: String, bundle: Bundle ->
            val lat = bundle.getDouble(CitiesListFragment.LATITUDE_KEY)
            val lng = bundle.getDouble(CitiesListFragment.LONGITUDE_KEY)

            viewModel.chooseCity(LatLng(lat, lng))
        }
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F

        const val MAP_ZOOM = 12F
    }
}