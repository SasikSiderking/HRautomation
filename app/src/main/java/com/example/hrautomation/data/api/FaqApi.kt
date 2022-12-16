package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.faq.FaqCategoryResponse
import com.example.hrautomation.data.model.faq.FaqQuestionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FaqApi {
    @GET("/faq/categories")
    suspend fun getFaqCategoriesResponse(): List<FaqCategoryResponse>

    @GET("/faq/categories/{id}")
    suspend fun getFaqQuestionsResponse(@Path("id") id: Long): List<FaqQuestionResponse>
}