package com.example.hrautomation.data.model.social.event

import java.util.Date

data class EventResponse(
    val id: Long,
    val name: String,
    val description: String,
    val date: Date,
    val address: String,
    val format: String,
    val pictureUrl: String?,
    val lat: Double,
    val lng: Double,
    val materials: List<EventMaterialResponse>
)