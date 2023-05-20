package com.example.hrautomation.presentation.model.social.event

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class EventItemDateFormat(
    override val id: Long,
    val date: String,
    val format: String
) : BaseListItem {
    companion object {
        const val delegateClassNumber = 2
    }
}