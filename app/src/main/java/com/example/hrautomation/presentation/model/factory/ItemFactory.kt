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
            val format: String
            if (listEvent.online) {
                format = stringResourceProvider.getString(R.string.format_online)
            } else {
                format = stringResourceProvider.getString(R.string.format_offline)
            }

            val timeLineColor: Int
            val timeLineIcon: Int
            if (listEvent.isOngoing) {
                timeLineColor = R.color.primary
                timeLineIcon = R.drawable.ic_baseline_event_available_24
            } else {
                timeLineColor = R.color.onSurface
                timeLineIcon = R.drawable.ic_baseline_disabled_by_default_24
            }

            ListEventItem(
                id = listEvent.id,
                name = listEvent.name,
                date = DateUtils.formatDate(listEvent.date),
                address = listEvent.address,
                pictureUrl = listEvent.pictureUrl,
                format = format,
                timeLineColor = timeLineColor,
                timeLineIcon = timeLineIcon
            )
        }
    }
}