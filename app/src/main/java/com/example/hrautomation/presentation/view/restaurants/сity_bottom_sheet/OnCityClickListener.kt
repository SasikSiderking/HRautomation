package com.example.hrautomation.presentation.view.restaurants.сity_bottom_sheet

import com.google.android.gms.maps.model.LatLng

fun interface OnCityClickListener {
    fun onClick(latLng: LatLng)
}