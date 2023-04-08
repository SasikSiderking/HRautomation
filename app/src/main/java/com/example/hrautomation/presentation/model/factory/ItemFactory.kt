package com.example.hrautomation.presentation.model.factory

import com.example.hrautomation.R
import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.social.ListEventItem
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.resources.StringResourceProvider
import javax.inject.Inject

interface ItemFactory {
    fun createListEventItems(listEvents: List<ListEvent>): List<BaseListItem>
}

class ItemFactoryImpl @Inject constructor(
    private val stringResourceProvider: StringResourceProvider
) : ItemFactory {
    override fun createListEventItems(listEvents: List<ListEvent>): List<BaseListItem> {
        return listEvents.map { listEvent ->
            val format = if (listEvent.online) {
                stringResourceProvider.getString(R.string.format_online)
            } else {
                stringResourceProvider.getString(R.string.format_offline)
            }
            ListEventItem(
                id = listEvent.id,
                name = listEvent.name,
                date = DateUtils.formatDate(listEvent.date),
                address = listEvent.address,
                pictureUrl = listEvent.pictureUrl,
                format = format
            )
        }
    }
}