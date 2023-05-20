package com.example.hrautomation.presentation.model.factory

import com.example.hrautomation.R
import com.example.hrautomation.domain.model.social.event.Event
import com.example.hrautomation.domain.model.social.event.EventMaterial
import com.example.hrautomation.domain.model.social.list_event.ListEvent
import com.example.hrautomation.presentation.model.social.event.EventItem
import com.example.hrautomation.presentation.model.social.event.EventItemAddress
import com.example.hrautomation.presentation.model.social.event.EventItemDateFormat
import com.example.hrautomation.presentation.model.social.event.EventItemDescription
import com.example.hrautomation.presentation.model.social.event.EventItemMap
import com.example.hrautomation.presentation.model.social.event.EventItemMaterial
import com.example.hrautomation.presentation.model.social.list_event.ListEventItem
import com.example.hrautomation.presentation.view.social.filter.EventFormat
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.resources.StringResourceProvider
import com.example.hrautomation.utils.social.SocialUtils
import javax.inject.Inject

interface EventItemFactory {
    fun createListEventItems(listEvents: List<ListEvent>): List<ListEventItem>

    fun createEventItem(event: Event): EventItem
}

class EventEventItemFactoryImpl @Inject constructor(
    private val stringResourceProvider: StringResourceProvider
) : EventItemFactory {

    override fun createListEventItems(listEvents: List<ListEvent>): List<ListEventItem> {
        return listEvents.map { listEvent ->
            val format: String = when (listEvent.format) {
                EventFormat.ONLINE.value -> stringResourceProvider.getString(R.string.format_online)
                EventFormat.OFFLINE.value -> stringResourceProvider.getString(R.string.format_offline)
                EventFormat.COMBINE.value -> stringResourceProvider.getString(R.string.format_combined)
                else -> ""
            }

            val timeLineColor: Int
            val timeLineIcon: Int
            if (SocialUtils.checkForOngoing(listEvent.date)) {
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
                pictureUrl = listEvent.pictureUrl,
                format = format,
                timeLineColor = timeLineColor,
                timeLineIcon = timeLineIcon
            )
        }
    }

    override fun createEventItem(event: Event): EventItem {
        return EventItem(
            event.id,
            event.name,
            event.pictureUrl,
            listOf(
                event.toEventItemDescription(),
                event.toEventItemDateFormat(),
                event.toEventItemAddress(),
                event.toEventItemMap()
            ) + event.materials.map { it.toEventItemMaterial() }
        )
    }

    private fun generateDelegateItemId(modelId: Long, delegateClassNumber: Int): Long {
        return modelId * 10 + delegateClassNumber
    }

    private fun EventMaterial.toEventItemMaterial(): EventItemMaterial {
        return EventItemMaterial(
            this.id,
            this.url,
            this.description,
        )
    }

    private fun Event.toEventItemDescription(): EventItemDescription {
        return EventItemDescription(
            generateDelegateItemId(
                this.id,
                EventItemDescription.delegateClassNumber
            ),
            this.description
        )
    }

    private fun Event.toEventItemDateFormat(): EventItemDateFormat {
        return EventItemDateFormat(
            generateDelegateItemId(
                this.id,
                EventItemDateFormat.delegateClassNumber
            ),
            DateUtils.formatDate(this.date),
            when (this.format) {
                EventFormat.ONLINE.value -> stringResourceProvider.getString(R.string.format_online)
                EventFormat.OFFLINE.value -> stringResourceProvider.getString(R.string.format_offline)
                EventFormat.COMBINE.value -> stringResourceProvider.getString(R.string.format_combined)
                else -> ""
            }
        )
    }

    private fun Event.toEventItemAddress(): EventItemAddress {
        return EventItemAddress(
            generateDelegateItemId(this.id, EventItemAddress.delegateClassNumber),
            this.address
        )
    }

    private fun Event.toEventItemMap(): EventItemMap {
        return EventItemMap(
            generateDelegateItemId(this.id, EventItemMap.delegateClassNumber),
            this.latLng
        )
    }

}