package com.example.hrautomation.presentation.view.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ItemEventBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.social.ListEventItemDisabled
import com.example.hrautomation.presentation.view.social.EventItemDisabledAdapterDelegate.EventDisabledViewHolder

class EventItemDisabledAdapterDelegate :
    BaseItemAdapterDelegate<ListEventItemDisabled, EventDisabledViewHolder>(),
    OnViewHolderClickListener<EventDisabledViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ListEventItemDisabled
    }

    override fun onCreateViewHolder(parent: ViewGroup): EventDisabledViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventDisabledViewHolder(binding, this)
    }

    override fun onBind(item: ListEventItemDisabled, holder: EventDisabledViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            timeline.setBackgroundColor(root.context.getColor(R.color.onSurface))

            Glide.with(eventImage)
                .load(item.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.img)
                .into(eventImage)
            timelineIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    root.context,
                    R.drawable.ic_baseline_disabled_by_default_24
                )
            )
            eventName.text = item.name
            eventDate.text = item.date
            eventFormat.text = item.format
        }
    }

    override fun onViewHolderClick(view: View, holder: EventDisabledViewHolder) = Unit

    class EventDisabledViewHolder(
        val binding: ItemEventBinding,
        clickListener: OnViewHolderClickListener<EventDisabledViewHolder>
    ) : ClickableViewHolder<EventDisabledViewHolder>(binding.root, clickListener)
}