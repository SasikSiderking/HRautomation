package com.example.hrautomation.presentation.model.social.event

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class EventItemMaterial(
    override val id: Long,
    val url: String,
    val description: String
) : BaseListItem