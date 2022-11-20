package com.example.hrautomation.presentation.view.product

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ProductCategoryAdapter : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(ProductCategoryItemAdapterDelegate())
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}