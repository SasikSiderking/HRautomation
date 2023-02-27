package com.example.hrautomation.presentation.view.restaurants.map

import com.google.android.gms.maps.model.LatLng

data class RestaurantsMapState(
    val chosenCityLatLng: LatLng,
    val chosenBuildingId: Long? = null,
    val chosenMarker: MarkerDelegate? = null
) {

    fun setCurrentCity(cityLatLng: LatLng): RestaurantsMapState {
        return this.copy(chosenCityLatLng = cityLatLng)
    }

    fun setChosenBuilding(buildingId: Long?, marker: MarkerDelegate?): RestaurantsMapState {
        return this.copy(chosenBuildingId = buildingId, chosenMarker = marker)
    }
}