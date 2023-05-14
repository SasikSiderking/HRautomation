package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.presentation.view.social.details.EventMaterialItem
import com.google.android.gms.maps.model.LatLng

data class EventItem(
    val id: Long,
    val name: String,
    val description: String,
    val date: String,
    val address: String,
    val format: String,
    val pictureUrl: String?,
    val latLng: LatLng,
    val materials: List<EventMaterialItem>
)