package com.example.hrautomation.presentation.view.social.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEventDetailsBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.social.event.EventItem
import com.example.hrautomation.presentation.view.social.details.map.EventMapActivity
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventDetailsActivity : BaseActivity<ActivityEventDetailsBinding>(), OnMapReadyCallback {
    override val bindingInflater: (LayoutInflater) -> ActivityEventDetailsBinding
        get() = { ActivityEventDetailsBinding.inflate(layoutInflater) }

    private val viewModel: EventDetailsViewModel by viewModels {
        viewModelFactory
    }

    private val eventMaterialAdapter = EventMaterialAdapter()

    private val eventId: Long by lazy {
        intent.getLongExtra(
            EVENT_ID_EXTRA,
            intent.getStringExtra(EVENT_ID_EXTRA)?.toLong() ?: DUMMY_EVENT_ID
        )
    }

    private lateinit var map: GoogleMap

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.loadData(eventId)
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
                viewModel.reload(eventId)
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }

            eventMaterialRecyclerView.adapter = eventMaterialAdapter
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
            eventMaterialAdapter.update(event.materials)
        }

        map.addMarker(MarkerOptions().position(event.latLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(event.latLng, MAP_ZOOM))
        map.setOnMapClickListener {
            openFullMap(event.latLng, event.name)
        }
        map.setOnMarkerClickListener {
            openFullMap(event.latLng, event.name)
            true
        }

        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    private fun openFullMap(eventLatLng: LatLng, eventName: String) {
        startActivity(EventMapActivity.createIntent(this, eventLatLng, eventName))
    }

    override fun onMapReady(map: GoogleMap) {
        map.uiSettings.setAllGesturesEnabled(false)
        this.map = map

        viewModel.exception.observe(this, exceptionObserver)
        viewModel.data.observe(this, eventObserver)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val DUMMY_EVENT_ID = 0L

        const val MAP_ZOOM = 16F

        const val EVENT_ID_EXTRA = "event_id_extra"
        fun createIntent(context: Context, eventId: Long): Intent {
            val intent = Intent(context, EventDetailsActivity::class.java)
            intent.putExtra(EVENT_ID_EXTRA, eventId)
            return intent
        }
    }
}