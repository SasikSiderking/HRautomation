package com.example.hrautomation.presentation.view.restaurants

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class RestaurantsAdapter(onRestaurantClickListener: OnRestaurantClickListener) :
    ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(RestaurantItemAdapterDelegate(onRestaurantClickListener))
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
//        TODO(заменить уже это везде)
    }
}