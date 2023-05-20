package com.example.hrautomation.presentation.model.social.event

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class EventItem(
    val id: Long,
    val name: String,
    val pictureUrl: String?,
    val delegateList: List<BaseListItem>
)