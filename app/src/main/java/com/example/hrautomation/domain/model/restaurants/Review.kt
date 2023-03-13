package com.example.hrautomation.domain.model.restaurants

import java.util.*

data class Review(
    val id: Long,
    val content: String,
    val rating: Float,
    val author: String,
    val pictureUrl: String,
    val date: Date
)