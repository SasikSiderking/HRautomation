package com.example.hrautomation.data.model.social.list_event

import com.example.hrautomation.domain.model.social.list_event.ListEvent

fun ListEventResponse.toListEvent(): ListEvent {
    return ListEvent(
        this.id,
        this.name,
        this.date,
        this.pictureUrl,
        this.format
    )
}