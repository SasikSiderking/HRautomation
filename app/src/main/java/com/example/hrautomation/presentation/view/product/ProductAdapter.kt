package com.example.hrautomation.presentation.view.product

import com.example.hrautomation.presentation.model.ProductItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ProductAdapter : ListDelegationAdapter<List<ProductItem>>() {
    init {
        delegatesManager.addDelegate(ProductListItemAdapterDelegate())
    }

    fun update(data: List<ProductItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}