package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.FaqQuestion
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class FaqQuestionItem(
    override val id: Long,
    val title: String,
    val description: String
) : BaseListItem

class FaqQuestionToFaqQuestionItemMapper : Mapper<FaqQuestion, FaqQuestionItem> {
    override fun convert(model: FaqQuestion): FaqQuestionItem = FaqQuestionItem(model.id, model.title, model.description)
}