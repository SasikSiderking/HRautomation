package com.example.hrautomation.presentation.view.restaurants.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrautomation.databinding.RestaurantsItemGapBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.restaurants.RestaurantsBottomSheetGapItem
import com.example.hrautomation.presentation.view.restaurants.list.OnRestaurantClickListener
import com.example.hrautomation.presentation.view.restaurants.restaurant.BottomSheetGapItemAdapterDelegate.BottomSheetGapViewHolder

class BottomSheetGapItemAdapterDelegate(private val onRestaurantClickListener: OnRestaurantClickListener) :
    BaseItemAdapterDelegate<RestaurantsBottomSheetGapItem, BottomSheetGapViewHolder>(),
    OnViewHolderClickListener<BottomSheetGapViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is RestaurantsBottomSheetGapItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): BottomSheetGapViewHolder {
        val binding = RestaurantsItemGapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetGapViewHolder(binding, this)
    }

    override fun onBind(item: RestaurantsBottomSheetGapItem, holder: BottomSheetGapViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            restaurantsGapItemText.text = item.text
        }
    }

    override fun onViewHolderClick(view: View, holder: BottomSheetGapViewHolder) {
        val item = getItemForViewHolder(holder)
        onRestaurantClickListener.onClick(item.id)
    }

    class BottomSheetGapViewHolder(
        val binding: RestaurantsItemGapBinding, clickListener: OnViewHolderClickListener<BottomSheetGapViewHolder>
    ) : ClickableViewHolder<BottomSheetGapViewHolder>(binding.root, clickListener)
}