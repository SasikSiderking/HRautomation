package com.example.hrautomation.data.model.social

import com.example.hrautomation.domain.model.social.ListEvent
import java.util.*

class ListEventResponse(
    val id: Long,
    val name: String,
    val date: Date,
    val address: String,
    val pictureUrl: String,
    val online: Boolean
)

fun ListEventResponse.toListEvent(): ListEvent {
    return ListEvent(
        this.id,
        this.name,
        this.date,
        this.address,
        this.pictureUrl,
        this.online
    )
}