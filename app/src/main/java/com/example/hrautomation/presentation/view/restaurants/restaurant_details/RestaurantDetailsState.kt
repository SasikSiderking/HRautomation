package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import com.example.hrautomation.presentation.model.restaurants.RestaurantItem
import com.example.hrautomation.presentation.model.restaurants.ReviewItem

data class RestaurantDetailsState(val restaurant: RestaurantItem, val reviews: List<ReviewItem>) {
    fun setReviews(reviewList: List<ReviewItem>): RestaurantDetailsState {
        return this.copy(reviews = reviewList)
    }
}