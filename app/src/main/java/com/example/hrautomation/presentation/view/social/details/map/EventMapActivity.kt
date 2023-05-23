package com.example.hrautomation.presentation.view.social.details.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ActivityEventMapBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventMapActivity : BaseActivity<ActivityEventMapBinding>(), OnMapReadyCallback {

    override val bindingInflater: (LayoutInflater) -> ActivityEventMapBinding
        get() = { ActivityEventMapBinding.inflate(layoutInflater) }

    override fun initInject() = Unit

    override fun initUI() {
        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(EVENT_NAME_EXTRA)
    }

    override fun initObserves() = Unit

    override fun initListeners() = Unit

    override fun onMapReady(map: GoogleMap) {
        val latLng = intent.getParcelableExtra<LatLng>(LAT_LNG_EXTRA)
        if (latLng != null) {
            map.addMarker(MarkerOptions().position(latLng))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val LAT_LNG_EXTRA = "event_latitude_extra"
        const val EVENT_NAME_EXTRA = "event_name_extra"

        const val MAP_ZOOM = 14F

        fun createIntent(context: Context, latLng: LatLng, eventName: String): Intent {
            val intent = Intent(context, EventMapActivity::class.java)
            intent.putExtra(LAT_LNG_EXTRA, latLng)
            intent.putExtra(EVENT_NAME_EXTRA, eventName)
            return intent
        }
    }
}