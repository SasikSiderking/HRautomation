package com.example.hrautomation.presentation.model

import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class ListedProductItem(
    val section: String,
    val img: String,
    val name: String,
    override val id: String = name
) : BaseListItem