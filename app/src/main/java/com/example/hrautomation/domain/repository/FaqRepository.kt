package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.FaqCategory

interface FaqRepository {
    suspend fun getFaqCategoryList(): Result<List<FaqCategory>>
}