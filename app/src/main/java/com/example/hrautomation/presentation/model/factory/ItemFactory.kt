package com.example.hrautomation.presentation.model.factory

import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.presentation.base.delegates.BaseListItem

interface ItemFactory {
    fun createListEventItems(domainModelList: List<ListEvent>): List<BaseListItem>
}