package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.ProductCategoryResponse
import com.example.hrautomation.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("/products/order/{id}")
    suspend fun orderProduct(@Path("id") id: Long): Response<Boolean>

    @GET("/products")
    suspend fun getProductResponseList(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): Response<List<ProductResponse>>

    @GET("/products/categories/{id}")
    suspend fun getProductsByCategory(@Path("id") id: Long): Response<List<ProductResponse>>

    @GET("/products/categories")
    suspend fun getProductCategoriesResponse(): Response<List<ProductCategoryResponse>>
}