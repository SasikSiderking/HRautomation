package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class ListEventItem(
    override val id: Long,
    val name: String,
    val date: String,
    val address: String,
    val pictureUrl: String?,
    val format: String
) : BaseListItem