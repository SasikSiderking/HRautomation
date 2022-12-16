package com.example.hrautomation.presentation.model.faq

import com.example.hrautomation.domain.model.faq.FaqQuestion
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