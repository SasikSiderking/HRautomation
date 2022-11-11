package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.FaqCategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface FaqApi {
    @GET("/faq/categories")
    suspend fun getFaqCategoriesResponse(): Response<List<FaqCategoryResponse>>
}