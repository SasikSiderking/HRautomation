package com.example.hrautomation.data.model.faq

import com.example.hrautomation.domain.model.faq.FaqQuestion
import com.example.hrautomation.utils.Mapper

data class FaqQuestionResponse(
    val id: Long,
    val title: String,
    val description: String,
    val categoryId: Long
)

class FaqQuestionResponseToFaqQuestionMapper : Mapper<FaqQuestionResponse, FaqQuestion> {
    override fun convert(model: FaqQuestionResponse): FaqQuestion = FaqQuestion(
        model.id,
        model.title,
        model.description
    )
}