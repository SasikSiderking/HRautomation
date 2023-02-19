package com.example.hrautomation.domain.model.restaurants

data class Building(
    val id: Long,
    val address: String,
    val lat: Double,
    val lng: Double,
    val restaurants: List<ListRestaurant>
)

data class ListRestaurant(
    val id: Long,
    val name: String,
    val status: String,
    val check: Int,
    val rating: Float,
)
