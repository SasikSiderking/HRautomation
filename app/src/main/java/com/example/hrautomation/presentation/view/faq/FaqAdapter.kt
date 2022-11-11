package com.example.hrautomation.presentation.view.faq

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class FaqAdapter(onFaqCategoryClickListener: OnFaqCategoryClickListener) : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(CategoryItemAdapterDelegate(onFaqCategoryClickListener))
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}