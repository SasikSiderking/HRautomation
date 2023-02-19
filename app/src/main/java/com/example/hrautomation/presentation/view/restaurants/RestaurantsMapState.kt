package com.example.hrautomation.presentation.view.restaurants

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

data class RestaurantsMapState(val chosenCityLatLng: LatLng, val chosenRestaurantId: Long?, val chosenMarker: Marker?) {

    fun setCurrentCity(cityLatLng: LatLng): RestaurantsMapState {
        return this.copy(chosenCityLatLng = cityLatLng)
    }

    fun setChosenRestaurant(restaurantId: Long?, marker: Marker?): RestaurantsMapState {
        return this.copy(chosenRestaurantId = restaurantId, chosenMarker = marker)
    }
}