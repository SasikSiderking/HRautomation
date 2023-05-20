package com.example.hrautomation.presentation.model.social.event

import com.example.hrautomation.presentation.base.delegates.BaseListItem

class EventItemAddress(
    override val id: Long,
    val text: String
) : BaseListItem {
    companion object {
        const val delegateClassNumber = 3
    }
}