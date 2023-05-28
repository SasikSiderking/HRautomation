package com.example.hrautomation.domain.model.social.event

import com.google.android.gms.maps.model.LatLng
import java.util.Date

data class Event(
    val id: Long,
    val name: String,
    val description: String?,
    val date: Date,
    val address: String?,
    val format: String,
    val pictureUrl: String?,
    val latLng: LatLng,
    val materials: List<EventMaterial>
)