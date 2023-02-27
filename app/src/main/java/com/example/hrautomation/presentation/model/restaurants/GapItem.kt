package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class GapItem(override val id: Long, val n: Int) : BaseListItem {
    val text: String = "И еще $n ресторанов"
}