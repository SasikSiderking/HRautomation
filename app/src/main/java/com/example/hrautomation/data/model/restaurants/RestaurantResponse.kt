package com.example.hrautomation.data.model.restaurants

data class RestaurantResponse(
    val id: Long,
    val name: String,
    val rating: Float,
    val status: RestaurantStatusResponse,
    val check: Float,
    val address: String,
    val lat: Double,
    val lng: Double,
    val reviews: List<ReviewResponse>
)
