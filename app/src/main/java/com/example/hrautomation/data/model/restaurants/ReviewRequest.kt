package com.example.hrautomation.data.model.restaurants

data class ReviewRequest(
    val content: String,
    val average: Int,
    val rating: Float
)
