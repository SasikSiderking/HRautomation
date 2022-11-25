package com.example.hrautomation.presentation.view.product

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ProductAdapter(onProductClickListener: OnProductClickListener) : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(ProductItemAdapterDelegate(onProductClickListener))
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}