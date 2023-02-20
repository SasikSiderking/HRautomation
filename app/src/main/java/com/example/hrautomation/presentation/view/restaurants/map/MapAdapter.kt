package com.example.hrautomation.presentation.view.restaurants.map

import android.content.Context
import com.example.hrautomation.R
import com.example.hrautomation.utils.BitmapUtils.DrawableToBitmapDescriptor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

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
            val marker: Marker? = map.addMarker(markerDelegate.markerOptions)
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

abstract class MarkerDelegate(var marker: Marker? = null, latLng: LatLng, buildingId: Long) {

    val id: Long = buildingId

    protected abstract val iconDefault: BitmapDescriptor

    protected abstract val iconChosen: BitmapDescriptor

    val markerOptions: MarkerOptions = MarkerOptions().position(latLng)
    fun setChosenIcon() {
        markerOptions.icon(iconChosen)
        marker?.setIcon(iconChosen)
    }

    fun setDefaultIcon() {
        markerOptions.icon(iconDefault)
        marker?.setIcon(iconDefault)
    }
}

class SingleMarker(context: Context, latLng: LatLng, buildingId: Long) :
    MarkerDelegate(latLng = latLng, buildingId = buildingId) {
    //        TODO(Разобраться что там ему нужена за тема)
    override val iconDefault = DrawableToBitmapDescriptor.convert(
        context.resources.getDrawable(R.drawable.ic_restaurants_marker_24)
    )
    override val iconChosen = DrawableToBitmapDescriptor.convert(
        context.resources.getDrawable(R.drawable.ic_restaurant_marker_chosen_24)
    )
}

class MultipleMarker(context: Context, latLng: LatLng, buildingId: Long) :
    MarkerDelegate(latLng = latLng, buildingId = buildingId) {

    override val iconDefault = DrawableToBitmapDescriptor.convert(
        context.resources.getDrawable(R.drawable.ic_multiple_restaurants_marker)
    )
    override val iconChosen = DrawableToBitmapDescriptor.convert(
        context.resources.getDrawable(R.drawable.ic_multiple_restaurants_marker_chosen)
    )
}