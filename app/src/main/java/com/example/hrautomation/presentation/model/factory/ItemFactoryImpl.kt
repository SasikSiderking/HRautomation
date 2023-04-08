package com.example.hrautomation.presentation.model.factory

import com.example.hrautomation.R
import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.social.ListEventItem
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.resources.StringResourceProvider
import javax.inject.Inject

class ItemFactoryImpl @Inject constructor(
    private val stringResourceProvider: StringResourceProvider
) : ItemFactory {
    override fun createListEventItems(domainModelList: List<ListEvent>): List<BaseListItem> {
        return domainModelList.map { listEvent ->

            val format = if (listEvent.online) {
                stringResourceProvider.getString(R.string.format_online)
            } else {
                stringResourceProvider.getString(R.string.format_offline)
            }
            ListEventItem(
                listEvent.id,
                listEvent.name,
                DateUtils.formatDate(listEvent.date),
                listEvent.address,
                listEvent.pictureUrl,
                format
            )

        }
    }
}