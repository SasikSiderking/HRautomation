package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.FaqCategory
import com.example.hrautomation.domain.model.FaqQuestion

interface FaqRepository {
    suspend fun getFaqCategoryList(): List<FaqCategory>

    suspend fun getFaqQuestionList(id: Long): List<FaqQuestion>
}