package com.example.hrautomation.presentation.view.restaurants

import com.google.android.gms.maps.model.LatLng

fun interface OnCityClickListener {
    fun onClick(latLng: LatLng)
}