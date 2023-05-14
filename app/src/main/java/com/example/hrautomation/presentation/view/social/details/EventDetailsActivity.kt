package com.example.hrautomation.presentation.view.social.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEventDetailsBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.social.EventItem
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class EventDetailsActivity : BaseActivity<ActivityEventDetailsBinding>(), OnMapReadyCallback {
    override val bindingInflater: (LayoutInflater) -> ActivityEventDetailsBinding
        get() = { ActivityEventDetailsBinding.inflate(layoutInflater) }

    private val viewModel: EventDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var map: GoogleMap

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)
    }

    override fun initUI() {
        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        with(binding) {

            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(coordinatorLayout),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )

            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() = Unit

    private val eventObserver = Observer<EventItem> { event ->
        with(binding) {
            toolbarLayout.title = event.name
            Glide.with(eventImage)
                .load(event.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(eventImage)
            description.setText(event.description)
            date.setText(event.date)
            format.setText(event.format)
            address.setText(event.address)
        }

        map.addMarker(MarkerOptions().position(event.latLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(event.latLng, MAP_ZOOM))

        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    private val onMapClickListener = GoogleMap.OnMapClickListener {
//        TODO: open the Full map
    }

    companion object {
        const val MAP_ZOOM = 12F
        fun createIntent(context: Context): Intent {
            return Intent(context, EventDetailsActivity::class.java)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        map.uiSettings.isScrollGesturesEnabled = false
        map.setOnMapClickListener(onMapClickListener)
        this.map = map

        viewModel.exception.observe(this, exceptionObserver)
        viewModel.data.observe(this, eventObserver)
    }
}