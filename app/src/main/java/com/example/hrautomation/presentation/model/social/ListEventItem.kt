package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import java.util.*

data class ListEventItem(
    override val id: Long,
    val name: String,
    val date: Date,
    val address: String,
    val pictureUrl: String?,
    val format: String
) : BaseListItem

fun ListEventItem.createFrom(model: ListEvent): ListEventItem {
    return ListEventItem(
        model.id,
        model.name,
        model.date,
        model.address,
        model.pictureUrl,
        if (model.online) {
            "online"
        } else {
            "offline"
        }
    )
}