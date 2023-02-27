package com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrautomation.databinding.ItemGapBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.restaurants.GapItem
import com.example.hrautomation.presentation.view.restaurants.list.OnRestaurantClickListener
import com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet.BottomSheetGapItemAdapterDelegate.BottomSheetGapViewHolder

class BottomSheetGapItemAdapterDelegate(private val onRestaurantClickListener: OnRestaurantClickListener) :
    BaseItemAdapterDelegate<GapItem, BottomSheetGapViewHolder>(),
    OnViewHolderClickListener<BottomSheetGapViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is GapItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): BottomSheetGapViewHolder {
        val binding = ItemGapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetGapViewHolder(binding, this)
    }

    override fun onBind(item: GapItem, holder: BottomSheetGapViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            textView.text = item.text
        }
    }

    override fun onViewHolderClick(view: View, holder: BottomSheetGapViewHolder) {
        val item = getItemForViewHolder(holder)
        onRestaurantClickListener.onClick(item.id)
    }

    class BottomSheetGapViewHolder(
        val binding: ItemGapBinding, clickListener: OnViewHolderClickListener<BottomSheetGapViewHolder>
    ) : ClickableViewHolder<BottomSheetGapViewHolder>(binding.root, clickListener)
}