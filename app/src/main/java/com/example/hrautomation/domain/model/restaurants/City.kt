package com.example.hrautomation.domain.model.restaurants

import java.io.Serializable

data class City(
    val id: Long,
    val name: String,
    val lat: Double,
    val lng: Double
) : Serializable