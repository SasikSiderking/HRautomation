package com.example.hrautomation.presentation.view.restaurants

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Updatable
import java.io.Closeable

class UpdatableViewAdapter<Item : BaseListItem, View>(
    private val items: List<Item> = emptyList(),
    private val view: View
) where View : Updatable<Item>, View : Closeable {

    fun updateView(id: Long) {
        val foundItem = items.find {
            it.id == id
        }

        foundItem?.let {
            view.update(it)
        }
    }

    fun closeView() {
        view.close()
    }

}