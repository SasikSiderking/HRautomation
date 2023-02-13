package com.example.hrautomation.presentation.view.restaurants

import android.graphics.Bitmap
import android.graphics.Canvas
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
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
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

    private var state: RestaurantsMapState = RestaurantsMapState(null, null, null)

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

        markerIcon = prepareIcon(R.drawable.ic_restaurants_marker_24)
        markerIconChosen = prepareIcon(R.drawable.ic_restaurant_marker_chosen_24)

        restaurantCardAdapter = UpdatableViewAdapter(binding.restaurantCard)


        viewModel.restaurantsMapState.observe(viewLifecycleOwner, stateObserver)

        supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onMapReady(map: GoogleMap) {
        this@RestaurantsMapFragment.map = map

        viewModel.data.observe(viewLifecycleOwner, restaurantsObserver)
        state.chosenCityLatLng?.let {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it, MAP_ZOOM))
        } ?: run {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, MAP_ZOOM))
        }
    }

    private val markerClickListener: OnMarkerClickListener = OnMarkerClickListener { marker: Marker ->

        viewModel.chooseRestaurant(marker.tag as Long, marker)

        return@OnMarkerClickListener true
    }

    private val onCardClickListener = OnCardClickListener { cardAction ->
        when (cardAction) {
            CardAction.CLOSE -> {
                viewModel.chooseRestaurant(null, null)
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
        state.chosenMarker?.setIcon(markerIcon)
        newState.chosenMarker?.setIcon(markerIconChosen)

        state = newState

        with(state) {
            chosenRestaurantId?.let {
                restaurantCardAdapter.updateView(it)
            } ?: run {
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
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(restaurant.lat, restaurant.lng), MAP_ZOOM))
            map.setOnMarkerClickListener(markerClickListener)

            marker?.tag = restaurant.id
        }
    }

    private fun prepareIcon(int: Int): BitmapDescriptor {
        //        TODO(Разобраться что там ему нужена за тема)
        val drawable = resources.getDrawable(int)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
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

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), MAP_ZOOM))
        }
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F

        val defaultLatLng: LatLng = LatLng(56.4884, 84.9480)

        const val MAP_ZOOM = 12F
    }
}