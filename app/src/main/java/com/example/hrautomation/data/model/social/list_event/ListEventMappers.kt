package com.example.hrautomation.data.model.social.list_event

import com.example.hrautomation.domain.model.social.list_event.ListEvent
import com.example.hrautomation.utils.date.DateUtils

fun ListEventResponse.toListEvent(): ListEvent {
    return ListEvent(
        this.id,
        this.name,
        DateUtils.parseDate(this.date),
        this.pictureUrl,
        this.format
    )
}