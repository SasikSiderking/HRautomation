package com.example.hrautomation.data.model.restaurants

import com.example.hrautomation.domain.model.restaurants.Review
import com.example.hrautomation.utils.Mapper
import java.util.Date

data class ReviewResponse(
    val id: Long,
    val content: String?,
    val rating: Float,
    val username: String?,
    val userpic: String?,
    val date: Date
)

class ReviewResponseToReviewMapper : Mapper<ReviewResponse, Review> {
    override fun convert(model: ReviewResponse): Review = Review(
        model.id,
        model.content ?: "",
        model.rating,
        model.username ?: "",
        model.userpic ?: "",
        model.date
    )
}