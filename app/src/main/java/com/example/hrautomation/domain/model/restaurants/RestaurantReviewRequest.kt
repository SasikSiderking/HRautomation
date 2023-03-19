package com.example.hrautomation.domain.model.restaurants

data class RestaurantReviewRequest(
    val restaurantId: Long,
    val userId: Long,
    val content: String,
    val check: Int,
    val rating: Float
)
