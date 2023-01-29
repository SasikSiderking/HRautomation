package com.example.hrautomation.data.model.restaurants

data class ReviewResponse(
    val content: String,
    val rating: Float,
    val author: String,
    val pictureUrl: String
)