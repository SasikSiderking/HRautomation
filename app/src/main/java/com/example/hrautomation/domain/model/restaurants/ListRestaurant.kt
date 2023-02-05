package com.example.hrautomation.domain.model.restaurants

data class ListRestaurant(
    val id: Long,
    val name: String,
    val address: String,
    val status: String,
    val check: Int,
    val rating: Float,
    val lat: Double,
    val lng: Double
)
