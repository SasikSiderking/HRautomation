package com.example.hrautomation.presentation.view.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ItemEventGridBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.social.ListEventItem
import com.example.hrautomation.presentation.view.social.EventItemAdapterDelegate.EventViewHolder

class EventItemAdapterDelegate :
    BaseItemAdapterDelegate<ListEventItem, EventViewHolder>(),
    OnViewHolderClickListener<EventViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ListEventItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): EventViewHolder {
        val binding = ItemEventGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, this)
    }

    override fun onBind(item: ListEventItem, holder: EventViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            Glide.with(eventImage)
                .load(item.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_confused)
                .into(eventImage)
            eventName.text = item.name
            eventDate.text = item.date.toString()
            eventFormat.text = item.format
            eventAddress.text = item.address
        }
    }

    override fun onViewHolderClick(view: View, holder: EventViewHolder) {
        val item = getItemForViewHolder(holder)
    }

    class EventViewHolder(
        val binding: ItemEventGridBinding,
        clickListener: OnViewHolderClickListener<EventViewHolder>
    ) : ClickableViewHolder<EventViewHolder>(binding.root, clickListener)
}