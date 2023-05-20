package com.example.hrautomation.data.model.faq

import com.example.hrautomation.domain.model.faq.FaqCategory
import com.example.hrautomation.utils.Mapper

data class FaqCategoryResponse(
    val id: Long,
    val name: String
)

class FaqCategoryResponseToFaqCategoryMapper : Mapper<FaqCategoryResponse, FaqCategory> {
    override fun convert(model: FaqCategoryResponse): FaqCategory = FaqCategory(model.id, model.name)
}