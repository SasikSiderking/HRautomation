package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.FaqApi2
import com.example.hrautomation.data.model.FaqCategoryResponseToFaqCategoryMapper
import com.example.hrautomation.domain.model.FaqCategory
import com.example.hrautomation.domain.repository.FaqRepository
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val api: FaqApi2,
    private val faqCategoryResponseToFaqCategoryMapper: FaqCategoryResponseToFaqCategoryMapper
) : FaqRepository {

    override suspend fun getFaqCategoryList(): Result<List<FaqCategory>> {
        val faqCategoryRes = api.getFaqCategoriesResponse()
        if (faqCategoryRes.isSuccessful) {
            faqCategoryRes.body()?.let { faqCategoryList ->
                return Result.success(faqCategoryList.map { faqCategoryResponseToFaqCategoryMapper.convert(it) })
            }
            return Result.failure(Exception("Категорий нет в базе"))
        } else {
            return Result.failure(Exception("Ошибка запроса: " + faqCategoryRes.code() + ": " + faqCategoryRes.errorBody()))
        }
    }
}