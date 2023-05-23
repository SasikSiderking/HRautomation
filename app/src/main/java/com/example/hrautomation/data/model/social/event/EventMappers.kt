package com.example.hrautomation.data.model.social.event

import com.example.hrautomation.domain.model.social.event.Event
import com.example.hrautomation.domain.model.social.event.EventMaterial
import com.google.android.gms.maps.model.LatLng

fun EventResponse.toEvent(): Event {
    return Event(
        this.id,
        this.name,
        this.description,
        this.date,
        this.address,
        this.format,
        this.pictureUrl,
        LatLng(this.lat, this.lng),
        this.materials.map { it.toEventMaterial() }
    )
}

fun EventMaterialResponse.toEventMaterial(): EventMaterial {
    return EventMaterial(
        this.id,
        this.url,
        this.description
    )
}