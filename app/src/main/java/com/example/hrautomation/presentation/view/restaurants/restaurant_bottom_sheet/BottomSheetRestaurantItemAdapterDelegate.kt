package com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrautomation.databinding.ItemBottomSheetRestaurantBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.view.restaurants.list.OnRestaurantClickListener
import com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet.BottomSheetRestaurantItemAdapterDelegate.BottomSheetRestaurantViewHolder

class BottomSheetRestaurantItemAdapterDelegate(private val onRestaurantClickListener: OnRestaurantClickListener) :
    BaseItemAdapterDelegate<ListRestaurantItem, BottomSheetRestaurantViewHolder>(),
    OnViewHolderClickListener<BottomSheetRestaurantViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ListRestaurantItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): BottomSheetRestaurantViewHolder {
        val binding = ItemBottomSheetRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetRestaurantViewHolder(binding, this)
    }

    override fun onBind(item: ListRestaurantItem, holder: BottomSheetRestaurantViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            restaurantName.text = item.name
            restaurantRating.text = item.rating.toString()
            restaurantAddress.text = item.address
            restaurantStatusCheck.text = item.statusAndCheck
        }
    }

    override fun onViewHolderClick(view: View, holder: BottomSheetRestaurantViewHolder) {
        val item = getItemForViewHolder(holder)
        onRestaurantClickListener.onClick(item.id)
    }

    class BottomSheetRestaurantViewHolder(
        val binding: ItemBottomSheetRestaurantBinding,
        clickListener: OnViewHolderClickListener<BottomSheetRestaurantViewHolder>
    ) : ClickableViewHolder<BottomSheetRestaurantViewHolder>(binding.root, clickListener)
}