package com.example.hrautomation.presentation.view.faq.question

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class QuestionAdapter : ListDelegationAdapter<List<BaseListItem>>() {
    init {
        delegatesManager.addDelegate(QuestionAdapterDelegate())
    }

    fun update(data: List<BaseListItem>) {
        setItems(data)
        notifyDataSetChanged()
    }
}