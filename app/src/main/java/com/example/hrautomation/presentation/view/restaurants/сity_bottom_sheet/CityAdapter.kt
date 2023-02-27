package com.example.hrautomation.presentation.view.restaurants.сity_bottom_sheet

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CityAdapter(onCityClickListener: OnCityClickListener) : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(CityItemAdapterDelegate(onCityClickListener))
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}