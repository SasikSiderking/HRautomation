package com.example.hrautomation.data.model.restaurants

data class RestaurantResponse(
    val id: Long,
    val name: String,
    val rating: Float,
    val status: String,
    val average: Int,
    val address: String,
    val lat: Double,
    val lng: Double,
    val reviews: List<ReviewResponse>
)
