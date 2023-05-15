package com.example.hrautomation.presentation.view.social

import com.example.hrautomation.presentation.model.social.ListEventItem

fun interface OnEventClickListener {
    fun onClick(event: ListEventItem)
}