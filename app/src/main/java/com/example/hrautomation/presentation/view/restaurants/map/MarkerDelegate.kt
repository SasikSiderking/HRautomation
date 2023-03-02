package com.example.hrautomation.presentation.view.restaurants.map

import android.content.Context
import android.graphics.Color
import com.example.hrautomation.R
import com.example.hrautomation.utils.BitmapUtils.MarkerIconProvider
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

sealed class MarkerDelegate(var marker: Marker? = null, latLng: LatLng, val id: Long) {

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
    MarkerDelegate(latLng = latLng, id = buildingId) {
    //        TODO(Разобраться что там ему нужена за тема)
    override val iconDefault = MarkerIconProvider.provide(
        context.resources.getDrawable(R.drawable.ic_restaurants_marker_24)
    )
    override val iconChosen = MarkerIconProvider.provide(
        context.resources.getDrawable(R.drawable.ic_restaurant_marker_chosen_24)
    )
}

class MultipleMarker(context: Context, latLng: LatLng, buildingId: Long, quantity: String) :
    MarkerDelegate(latLng = latLng, id = buildingId) {

    override val iconDefault = MarkerIconProvider.provideWithText(
        context.resources.getDrawable(R.drawable.ic_multiple_restaurants_marker_24),
        quantity,
        Color.valueOf(context.getColor(R.color.white))
    )
    override val iconChosen = MarkerIconProvider.provideWithText(
        drawable = context.resources.getDrawable(R.drawable.ic_multiple_restaurants_marker_chosen_24),
        text = quantity,
        color = Color.valueOf(context.getColor(R.color.primary))
    )
}