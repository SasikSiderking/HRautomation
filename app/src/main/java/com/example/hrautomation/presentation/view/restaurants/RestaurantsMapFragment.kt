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
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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

    private lateinit var cityFragment: CityFragment

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

        binding.restaurantCard.setListener(onCardClickListener)
        viewModel.chosenRestaurant.observe(viewLifecycleOwner, chosenRestaurantObserver)

        binding.choseCityButton.setOnClickListener(choseCityClickListener)

        supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.elevation = requireContext().dpToPx(TOOLBAR_ELEVATION).toFloat()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this@RestaurantsMapFragment.map = map

        viewModel.data.observe(viewLifecycleOwner, restaurantsObserver)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, MAP_ZOOM))
    }

    private val restaurantsObserver = Observer<List<ListRestaurantItem>> { listRestaurants ->
        listRestaurants.forEach { restaurant ->
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(restaurant.lat, restaurant.lng))
                    .title(restaurant.name)
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(restaurant.lat, restaurant.lng), MAP_ZOOM))
            map.setOnMarkerClickListener(markerClickListener)
        }
    }

    private val markerClickListener: OnMarkerClickListener = OnMarkerClickListener { marker: Marker ->
        val restaurant = viewModel.data.value?.find { restaurant: ListRestaurantItem ->
            restaurant.lat == marker.position.latitude && restaurant.lng == marker.position.longitude
        }
        viewModel.choseRestaurant(restaurant)
        return@OnMarkerClickListener true
    }

    private val onCardClickListener = OnCardClickListener { cardAction ->
        when (cardAction) {
            CardAction.CrossClicked -> {
                viewModel.choseRestaurant(null)
            }
            CardAction.DetailsClicked -> {
//                TODO(Открыть активити с фулл рестораном)
            }
        }
    }

    private val chosenRestaurantObserver = Observer<ListRestaurantItem?> { restaurant: ListRestaurantItem? ->
        binding.restaurantCard.updateViewData(restaurant)
    }

    private val choseCityClickListener = OnClickListener {
        cityFragment = CityFragment(OnCityClickListener { latLng ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latLng.latitude, latLng.longitude), MAP_ZOOM))
            cityFragment.dismiss()
        })
        cityFragment.show(childFragmentManager, CityFragment.TAG)
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F

        val defaultLatLng: LatLng = LatLng(56.4884, 84.9480)

        const val MAP_ZOOM = 14F
    }
}