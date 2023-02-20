package com.example.hrautomation.presentation.view.restaurants.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrautomation.databinding.ItemResturantBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.view.restaurants.list.RestaurantItemAdapterDelegate.RestaurantViewHolder

class RestaurantItemAdapterDelegate(private val onRestaurantClickListener: OnRestaurantClickListener) :
    BaseItemAdapterDelegate<ListRestaurantItem, RestaurantViewHolder>(), OnViewHolderClickListener<RestaurantViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ListRestaurantItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RestaurantViewHolder {
        val binding = ItemResturantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding, this)
    }

    override fun onBind(item: ListRestaurantItem, holder: RestaurantViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            restaurantName.text = item.name
            restaurantRating.text = item.rating.toString()
            restaurantAddress.text = item.address
            restaurantStatusCheck.text = item.statusAndCheck
        }
    }

    override fun onViewHolderClick(view: View, holder: RestaurantViewHolder) {
        val item = getItemForViewHolder(holder)
        onRestaurantClickListener.onClick(item.id)
    }

    class RestaurantViewHolder(
        val binding: ItemResturantBinding, clickListener: OnViewHolderClickListener<RestaurantViewHolder>
    ) : ClickableViewHolder<RestaurantViewHolder>(binding.root, clickListener)
}