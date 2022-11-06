package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ColleaguesAdapter(onColleagueClickListener: OnColleagueClickListener) : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(ColleaguesItemAdapterDelegate(onColleagueClickListener))
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}