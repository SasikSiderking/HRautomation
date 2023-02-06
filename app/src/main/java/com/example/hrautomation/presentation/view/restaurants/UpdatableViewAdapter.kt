package com.example.hrautomation.presentation.view.restaurants

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Updatable

class UpdatableViewAdapter<Item : BaseListItem, View : Updatable<Item>>(
    private val items: List<Item> = emptyList(),
    private val view: View
) {

    fun updateView(id: Long) {
        val foundItem = items.find {
            it.id == id
        }

        foundItem?.let {
            view.update(it)
        }
    }

}