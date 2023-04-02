package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.R
import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.resources.StringResourceProvider

fun ListEvent.toListEventItem(stringResourceProvider: StringResourceProvider): BaseListItem {
    return if (isOngoing) {
        ListEventItem(
            id,
            name,
            DateUtils.formatDate(date),
            address,
            pictureUrl,
            if (online) {
                stringResourceProvider.getString(R.string.format_online)
            } else {
                stringResourceProvider.getString(R.string.format_offline)
            }
        )
    } else {
        ListEventItemDisabled(
            id,
            name,
            DateUtils.formatDate(date),
            address,
            pictureUrl,
            if (online) {
                stringResourceProvider.getString(R.string.format_online)
            } else {
                stringResourceProvider.getString(R.string.format_offline)
            }
        )
    }
}