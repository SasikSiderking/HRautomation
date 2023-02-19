package com.example.hrautomation.domain.repository

import com.google.android.gms.maps.model.LatLng

interface CachedCityLatLngRepository {
    fun setLatLng(latLng: LatLng)
    fun getLatLng(): LatLng?
}