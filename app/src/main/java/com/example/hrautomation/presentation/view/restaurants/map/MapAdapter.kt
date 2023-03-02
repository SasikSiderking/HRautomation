package com.example.hrautomation.presentation.view.restaurants.map

import android.content.Context
import com.example.hrautomation.presentation.model.restaurants.BuildingItem
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

    fun setMarkers(buildings: List<BuildingItem>, context: Context) {

        val markerDelegates = createMarkers(buildings, context)

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

    private fun createMarkers(buildings: List<BuildingItem>, context: Context): List<MarkerDelegate> {
        return buildings.map { building ->
            if (building.restaurants.size > 1) {
                MultipleMarker(
                    context = context,
                    latLng = LatLng(building.lat, building.lng),
                    buildingId = building.id,
                    quantity = building.restaurants.size.toString()
                )
            } else {
                SingleMarker(
                    context = context,
                    latLng = LatLng(building.lat, building.lng),
                    buildingId = building.id
                )
            }
        }
    }
}

fun interface OnMarkerDelegateClickListener {
    fun onClick(markerDelegate: MarkerDelegate)
}