package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.domain.model.restaurants.Review
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ReviewItem(
    override val id: Long,
    val content: String,
    val rating: Float,
    val author: String,
    val pictureUrl: String
) : BaseListItem

class ReviewToReviewItemMapper : Mapper<Review, ReviewItem> {
    override fun convert(model: Review): ReviewItem = ReviewItem(
        model.id,
        model.content,
        model.rating,
        model.author,
        model.pictureUrl
    )
}