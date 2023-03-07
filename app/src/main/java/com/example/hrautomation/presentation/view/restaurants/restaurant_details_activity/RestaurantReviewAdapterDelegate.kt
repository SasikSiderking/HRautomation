package com.example.hrautomation.presentation.view.restaurants.restaurant_details_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ItemRestaurantReviewBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.restaurants.ReviewItem
import com.example.hrautomation.presentation.view.restaurants.restaurant_details_activity.RestaurantReviewAdapterDelegate.RestaurantReviewViewHolder

class RestaurantReviewAdapterDelegate :
    BaseItemAdapterDelegate<ReviewItem, RestaurantReviewViewHolder>(),
    OnViewHolderClickListener<RestaurantReviewViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ReviewItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RestaurantReviewViewHolder {
        val binding = ItemRestaurantReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantReviewViewHolder(binding, this)
    }

    override fun onBind(item: ReviewItem, holder: RestaurantReviewViewHolder, payloads: List<Any>) {
        with(holder.binding) {
            Glide.with(employeeImage)
                .load(item.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_confused)
                .into(employeeImage)
            employeeName.text = item.author
            reviewRating.rating = item.rating
//            TODO(Date in review)
            reviewDate.text = "04.05.2021"
            reviewContent.text = item.content
        }
    }

    override fun onViewHolderClick(view: View, holder: RestaurantReviewViewHolder) = Unit

    class RestaurantReviewViewHolder(
        val binding: ItemRestaurantReviewBinding,
        clickListener: OnViewHolderClickListener<RestaurantReviewViewHolder>
    ) : ClickableViewHolder<RestaurantReviewViewHolder>(binding.root, clickListener)
}