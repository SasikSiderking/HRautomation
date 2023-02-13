package com.example.hrautomation.presentation.view.restaurants

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class RestaurantsMapState(var chosenCityLatLng: LatLng?, var chosenRestaurantId: Long?, var chosenMarker: Marker?) {

    fun setCurrentCity(cityLatLng: LatLng?): RestaurantsMapState {
        return RestaurantsMapState(cityLatLng, this.chosenRestaurantId, this.chosenMarker)
    }

    fun setChosenRestaurant(restaurantId: Long?, marker: Marker?): RestaurantsMapState {
        return RestaurantsMapState(this.chosenCityLatLng, restaurantId, marker)
    }
}