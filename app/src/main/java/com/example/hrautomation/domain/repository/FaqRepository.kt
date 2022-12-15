package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.faq.FaqCategory
import com.example.hrautomation.domain.model.faq.FaqQuestion

interface FaqRepository {
    suspend fun getFaqCategoryList(): List<FaqCategory>

    suspend fun getFaqQuestionList(id: Long): List<FaqQuestion>
}