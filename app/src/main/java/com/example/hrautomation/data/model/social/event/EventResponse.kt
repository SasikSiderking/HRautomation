package com.example.hrautomation.data.model.social.event

data class EventResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val date: String,
    val address: String?,
    val format: String,
    val pictureUrl: String?,
    val lat: Double,
    val lng: Double,
    val materials: List<EventMaterialResponse>
)