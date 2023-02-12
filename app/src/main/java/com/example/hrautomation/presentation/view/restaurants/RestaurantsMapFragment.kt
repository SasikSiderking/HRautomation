package com.example.hrautomation.presentation.view.restaurants

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
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
import com.google.android.gms.maps.model.*
import javax.inject.Inject


class RestaurantsMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding!!

    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var map: GoogleMap

    private lateinit var cityFragment: CitiesListFragment

    private lateinit var restaurantCardAdapter: UpdatableViewAdapter<ListRestaurantItem, RestaurantCard>

    private lateinit var chosenMarker: Marker

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

        restaurantCardAdapter = UpdatableViewAdapter(binding.restaurantCard)

        viewModel.chosenRestaurantId.observe(viewLifecycleOwner, chosenRestaurantObserver)

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

    override fun onMapReady(map: GoogleMap) {
        this@RestaurantsMapFragment.map = map

        viewModel.data.observe(viewLifecycleOwner, restaurantsObserver)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, MAP_ZOOM))
    }

    private val restaurantsObserver = Observer<List<ListRestaurantItem>> { listRestaurants ->

        restaurantCardAdapter.setItems(listRestaurants)

//        TODO(Разобраться что там ему нужена за тема)
        val markerDrawable = resources.getDrawable(R.drawable.ic_restaurants_marker_24)
        val markerIcon = getMarkerIconFromDrawable(markerDrawable)

        listRestaurants.forEach { restaurant ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(restaurant.lat, restaurant.lng))
                    .icon(markerIcon)
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(restaurant.lat, restaurant.lng), MAP_ZOOM))
            map.setOnMarkerClickListener(markerClickListener)

            marker?.tag = restaurant.id
//            marker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.restaurants_map))
        }
    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private val chosenRestaurantObserver = Observer<Long?> { chosenRestaurantId ->
        if (chosenRestaurantId != null) {
            restaurantCardAdapter.updateView(chosenRestaurantId)
        } else {
            restaurantCardAdapter.closeView()
        }
    }

    private val markerClickListener: OnMarkerClickListener = OnMarkerClickListener { marker: Marker ->

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(50f))

        viewModel.chooseRestaurant(marker.tag as Long)

        return@OnMarkerClickListener true
    }

    private val onCardClickListener = OnCardClickListener { cardAction ->
        when (cardAction) {
            CardAction.CLOSE -> {
                viewModel.chooseRestaurant(null)
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

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F

        val defaultLatLng: LatLng = LatLng(56.4884, 84.9480)

        const val MAP_ZOOM = 12F
    }
}