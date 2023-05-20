package com.example.hrautomation.data.model.social

import com.example.hrautomation.domain.model.social.ListEvent

fun ListEventResponse.toListEvent(): ListEvent {
    return ListEvent(
        this.id,
        this.name,
        this.date,
        this.pictureUrl,
        this.format
    )
}