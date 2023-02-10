package com.example.hrautomation.presentation.view.restaurants

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Updatable
import java.io.Closeable

class UpdatableViewAdapter<Item : BaseListItem, View>(
    private val view: View
) where View : Updatable<Item>, View : Closeable {

    private var items: List<Item> = emptyList()

    private var currentItemId: Long? = null

    fun updateView(id: Long) {

        //по-сути тут этого делать не надо но концептуально должно быть
        currentItemId = id

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

    fun setItems(items: List<Item>) {
        this.items = items
        currentItemId?.let {
            updateView(it)
        }
    }

}