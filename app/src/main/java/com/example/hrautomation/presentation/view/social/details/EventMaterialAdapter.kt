package com.example.hrautomation.presentation.view.social.details

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class EventMaterialAdapter : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(EventMaterialAdapterDelegate())
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}