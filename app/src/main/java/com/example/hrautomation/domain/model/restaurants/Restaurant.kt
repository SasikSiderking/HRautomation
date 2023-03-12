package com.example.hrautomation.domain.model.restaurants

data class Restaurant(
    val id: Long,
    val name: String,
    val rating: Float,
    val status: String,
    val check: Int,
    val address: String
)