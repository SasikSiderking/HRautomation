package com.example.hrautomation.presentation.base.delegates

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.R
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

abstract class BaseItemAdapterDelegate<I : BaseListItem, VH : RecyclerView.ViewHolder> : AbsListItemAdapterDelegate<I, BaseListItem, VH>() {

    override fun isForViewType(item: BaseListItem, items: MutableList<BaseListItem>, position: Int): Boolean {
        return isForViewType(item)
    }

    override fun onBindViewHolder(item: I, holder: VH, payloads: MutableList<Any>) {
        holder.itemView.setTag(R.id.base_item_tag_key_id, item)
        onBind(item, holder, payloads)
    }

    fun getItemForViewHolder(holder: VH): I = getItemFromItemView(holder.itemView)

    fun getItemFromItemView(itemView: View): I {
        return itemView.getTag(R.id.base_item_tag_key_id) as? I
            ?: throw IllegalStateException("Item is null or has a wrong type")
    }

    abstract fun isForViewType(item: BaseListItem): Boolean

    abstract fun onBind(item: I, holder: VH, payloads: List<Any>)
}
