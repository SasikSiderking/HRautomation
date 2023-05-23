package com.example.hrautomation.presentation.view.social.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hrautomation.databinding.ItemEventMaterialBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.social.event.EventMaterialItem
import com.example.hrautomation.presentation.view.social.details.EventMaterialAdapterDelegate.EventMaterialViewHolder

class EventMaterialAdapterDelegate : BaseItemAdapterDelegate<EventMaterialItem, EventMaterialViewHolder>() {
    override fun isForViewType(item: BaseListItem): Boolean {
        return item is EventMaterialItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): EventMaterialViewHolder {
        val binding = ItemEventMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventMaterialViewHolder(binding)
    }

    override fun onBind(item: EventMaterialItem, holder: EventMaterialViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            materialDescription.text = item.description
            materialUrl.text = item.url
        }
    }

    class EventMaterialViewHolder(val binding: ItemEventMaterialBinding) : ViewHolder(binding.root)
}