package com.example.hrautomation.presentation.model.faq

import com.example.hrautomation.domain.model.faq.FaqCategory
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class FaqCategoryItem(
    override val id: Long,
    val name: String
) : BaseListItem

class FaqCategoryToFaqCategoryItemMapper : Mapper<FaqCategory, FaqCategoryItem> {
    override fun convert(model: FaqCategory): FaqCategoryItem = FaqCategoryItem(model.id, model.name)
}