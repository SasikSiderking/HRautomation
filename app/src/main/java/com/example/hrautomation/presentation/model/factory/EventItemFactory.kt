package com.example.hrautomation.presentation.model.factory

import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import com.example.hrautomation.R
import com.example.hrautomation.domain.model.social.event.Event
import com.example.hrautomation.domain.model.social.event.EventMaterial
import com.example.hrautomation.domain.model.social.list_event.ListEvent
import com.example.hrautomation.presentation.model.social.event.EventItem
import com.example.hrautomation.presentation.model.social.event.EventMaterialItem
import com.example.hrautomation.presentation.model.social.list_event.ListEventItem
import com.example.hrautomation.presentation.view.social.filter.EventFormat
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.resources.ConfigurationProvider
import com.example.hrautomation.utils.resources.StringResourceProvider
import com.example.hrautomation.utils.social.SocialUtils
import javax.inject.Inject

interface EventItemFactory {
    fun createListEventItems(listEvents: List<ListEvent>): List<ListEventItem>

    fun createEventItem(event: Event): EventItem
}

class EventItemFactoryImpl @Inject constructor(
    private val stringResourceProvider: StringResourceProvider,
    private val configurationProvider: ConfigurationProvider
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
                date = DateUtils.formatDateToDayMonth(listEvent.date, configurationProvider),
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
            event.description ?: "",
            DateUtils.formatDateToDayMonthAndLocale(event.date, configurationProvider, stringResourceProvider),
            event.address ?: "",
            when (event.format) {
                EventFormat.ONLINE.value -> stringResourceProvider.getString(R.string.format_online)
                EventFormat.OFFLINE.value -> stringResourceProvider.getString(R.string.format_offline)
                EventFormat.COMBINE.value -> stringResourceProvider.getString(R.string.format_combined)
                else -> ""
            },
            event.pictureUrl,
            event.latLng,
            event.materials.map { it.toEventItemMaterial() }
        )
    }

    private fun EventMaterial.toEventItemMaterial(): EventMaterialItem {

        val spannableString = SpannableString(this.description)
        spannableString.setSpan(URLSpan(this.url), 0, this.description.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return EventMaterialItem(
            this.id,
            spannableString
        )
    }
}