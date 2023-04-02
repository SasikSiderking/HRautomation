package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.date.DateUtils

data class ListEventItem(
    override val id: Long,
    val name: String,
    val date: String,
    val address: String,
    val pictureUrl: String?,
    val format: String
) : BaseListItem {
    companion object {
        fun createFrom(model: ListEvent): ListEventItem {
            return ListEventItem(
                model.id,
                model.name,
                DateUtils.formatDate(model.date),
                model.address,
                model.pictureUrl,
                if (model.online) {
                    "online"
                } else {
                    "offline"
                }
            )
        }
    }
}