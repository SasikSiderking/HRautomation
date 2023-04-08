package com.example.hrautomation.data.model.social

import com.example.hrautomation.domain.model.social.ListEvent

fun ListEventResponse.toListEvent(ongoing: Boolean): ListEvent {
    return ListEvent(
        this.id,
        this.name,
        this.date,
        this.address,
        this.pictureUrl,
        this.online,
        ongoing
    )
}