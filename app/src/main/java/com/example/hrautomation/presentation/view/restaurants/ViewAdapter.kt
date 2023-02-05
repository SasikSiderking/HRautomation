package com.example.hrautomation.presentation.view.restaurants

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Updatable

class ViewAdapter<Items : BaseListItem?, View : Updatable<Items>>(
    private val items: List<Items>,
    private val view: View
) {

    fun updateView(id: Long) {
        val foundItem = items.find {
            it != null && it.id == id
        }

        foundItem?.let {
            view.update(it)
        }
    }
}