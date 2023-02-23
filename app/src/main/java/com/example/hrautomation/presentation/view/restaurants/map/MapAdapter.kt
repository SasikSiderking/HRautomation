package com.example.hrautomation.presentation.view.restaurants.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MapAdapter(private val map: GoogleMap) {

    private var listener: OnMarkerDelegateClickListener? = null

    private var chosenMarker: MarkerDelegate? = null

    private var list: List<MarkerDelegate> = emptyList()

    init {
        initClickListener()
    }

    fun chooseMarker(markerDelegateId: Long) {
        chosenMarker?.setDefaultIcon()
        val foundItem = list.find { it.id == markerDelegateId }
        foundItem?.setChosenIcon()

        chosenMarker = foundItem
    }

    fun unChooseMarker() {
        chosenMarker?.setDefaultIcon()
    }

    fun setMarkers(markerDelegates: List<MarkerDelegate>) {
        markerDelegates.forEach { markerDelegate ->
            markerDelegate.setDefaultIcon()
            val marker = map.addMarker(markerDelegate.markerOptions)
            marker?.tag = markerDelegate.id
            markerDelegate.marker = marker
        }
        list = markerDelegates
    }

    fun setMarkerClickListener(listener: OnMarkerDelegateClickListener) {
        this.listener = listener
    }

    fun moveCamera(latLng: LatLng, zoom: Float) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun initClickListener() {
        map.setOnMarkerClickListener { marker: Marker ->
            val foundItem = list.find { it.id == marker.tag }
            if (foundItem != null) {
                this.listener?.onClick(foundItem)
            }
            true
        }
    }
}

fun interface OnMarkerDelegateClickListener {
    fun onClick(markerDelegate: MarkerDelegate)
}