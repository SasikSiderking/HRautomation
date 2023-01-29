package com.example.hrautomation.domain.model.restaurants

import com.example.hrautomation.data.model.restaurants.RestaurantStatusResponse

data class ListRestaurant(
    val id: Long,
    val name: String,
    val address: String,
    val status: RestaurantStatusResponse,
    val check: Float,
    val rating: Float,
    val lat: Double,
    val lng: Double
)
