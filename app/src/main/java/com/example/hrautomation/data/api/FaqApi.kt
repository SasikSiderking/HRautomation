package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.FaqCategoryResponse
import com.example.hrautomation.data.model.FaqQuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FaqApi {
    @GET("/faq/categories")
    suspend fun getFaqCategoriesResponse(): Response<List<FaqCategoryResponse>>

    @GET("/faq//categories/{id}")
    suspend fun getFaqQuestionsResponse(@Path("id") id: Long): Response<List<FaqQuestionResponse>>
}