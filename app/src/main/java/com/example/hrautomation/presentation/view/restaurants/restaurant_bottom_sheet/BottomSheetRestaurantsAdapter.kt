package com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.restaurants.list.OnRestaurantClickListener
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class BottomSheetRestaurantsAdapter(onRestaurantClickListener: OnRestaurantClickListener) :
    ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(BottomSheetRestaurantItemAdapterDelegate(onRestaurantClickListener))
        delegatesManager.addDelegate(BottomSheetGapItemAdapterDelegate(onRestaurantClickListener))
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}