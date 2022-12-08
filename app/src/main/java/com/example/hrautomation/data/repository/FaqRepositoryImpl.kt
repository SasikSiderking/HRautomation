package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.FaqApi
import com.example.hrautomation.data.model.FaqCategoryResponseToFaqCategoryMapper
import com.example.hrautomation.data.model.FaqQuestionResponseToFaqQuestionMapper
import com.example.hrautomation.domain.model.FaqCategory
import com.example.hrautomation.domain.model.FaqQuestion
import com.example.hrautomation.domain.repository.FaqRepository
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val api: FaqApi,
    private val faqCategoryResponseToFaqCategoryMapper: FaqCategoryResponseToFaqCategoryMapper,
    private val faqQuestionResponseToFaqQuestionMapper: FaqQuestionResponseToFaqQuestionMapper
) : FaqRepository {

    override suspend fun getFaqCategoryList(): List<FaqCategory> {
        return api.getFaqCategoriesResponse().map { faqCategoryResponseToFaqCategoryMapper.convert(it) }
    }

    override suspend fun getFaqQuestionList(id: Long): List<FaqQuestion> {
        return api.getFaqQuestionsResponse(id).map { faqQuestionResponseToFaqQuestionMapper.convert(it) }
    }
}