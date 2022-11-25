package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.FaqApi
import com.example.hrautomation.data.model.FaqCategoryResponseToFaqCategoryMapper
import com.example.hrautomation.data.model.FaqQuestionResponseToFaqQuestionMapper
import com.example.hrautomation.domain.model.FaqCategory
import com.example.hrautomation.domain.model.FaqQuestion
import com.example.hrautomation.domain.repository.FaqRepository
import com.example.hrautomation.utils.asResult
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val api: FaqApi,
    private val faqCategoryResponseToFaqCategoryMapper: FaqCategoryResponseToFaqCategoryMapper,
    private val faqQuestionResponseToFaqQuestionMapper: FaqQuestionResponseToFaqQuestionMapper
) : FaqRepository {

    override suspend fun getFaqCategoryList(): Result<List<FaqCategory>> {
        return api.getFaqCategoriesResponse().asResult { faqCategoryList ->
            faqCategoryList.map { faqCategoryResponseToFaqCategoryMapper.convert(it) }
        }
    }

    override suspend fun getFaqQuestionList(id: Long): Result<List<FaqQuestion>> {
        return api.getFaqQuestionsResponse(id).asResult { faqQuestionList ->
            faqQuestionList.map { faqQuestionResponseToFaqQuestionMapper.convert(it) }
        }
    }
}