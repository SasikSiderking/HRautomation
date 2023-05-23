package com.example.hrautomation.presentation.model.social.list_event

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class ListEventItem(
    override val id: Long,
    val name: String,
    val date: String,
    val pictureUrl: String?,
    val format: String,
    val timeLineColor: Int,
    val timeLineIcon: Int
) : BaseListItem