package com.example.hrautomation.presentation.view.restaurants

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
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.view.restaurants.сity.CitiesListFragment
import com.example.hrautomation.utils.BitmapUtils.DrawableToBitmapDescriptor
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class RestaurantsMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding!!

    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var map: GoogleMap

    private lateinit var markerIcon: BitmapDescriptor
    private lateinit var markerIconChosen: BitmapDescriptor

    private lateinit var cityFragment: CitiesListFragment

    private lateinit var restaurantCardAdapter: UpdatableViewAdapter<ListRestaurantItem, RestaurantCard>

    private var chosenMarker: Marker? = null
    private var chosenCityLatLng: LatLng? = null

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

        //        TODO(Разобраться что там ему нужена за тема)
        markerIcon = DrawableToBitmapDescriptor.convert(
            resources.getDrawable(R.drawable.ic_restaurants_marker_24)
        )
        markerIconChosen = DrawableToBitmapDescriptor.convert(
            resources.getDrawable(R.drawable.ic_restaurant_marker_chosen_24)
        )

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onMapReady(map: GoogleMap) {
        this@RestaurantsMapFragment.map = map

        viewModel.data.observe(viewLifecycleOwner, restaurantsObserver)
        viewModel.restaurantsMapState.observe(viewLifecycleOwner, stateObserver)
    }

    private val markerClickListener: OnMarkerClickListener = OnMarkerClickListener { marker: Marker ->

        viewModel.chooseRestaurant(marker.tag as Long, marker)

        return@OnMarkerClickListener true
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
        if (chosenCityLatLng != newState.chosenCityLatLng) {
            chosenCityLatLng = newState.chosenCityLatLng
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenCityLatLng!!, MAP_ZOOM))
        }

        chosenMarker?.setIcon(markerIcon)
        chosenMarker = newState.chosenMarker
        newState.chosenMarker?.setIcon(markerIconChosen)

        with(newState) {
            if (chosenRestaurantId != null) {
                restaurantCardAdapter.updateView(chosenRestaurantId)
            } else {
                restaurantCardAdapter.closeView()
            }
        }
    }

    private val restaurantsObserver = Observer<List<ListRestaurantItem>> { listRestaurants ->
        restaurantCardAdapter.setItems(listRestaurants)

        listRestaurants.forEach { restaurant ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(restaurant.lat, restaurant.lng))
                    .icon(markerIcon)
            )
            map.setOnMarkerClickListener(markerClickListener)

            marker?.tag = restaurant.id
        }
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