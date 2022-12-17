package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.products.ProductCategoryResponse
import com.example.hrautomation.data.model.products.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("/products/order/{id}")
    suspend fun orderProduct(@Path("id") id: Long)

    @GET("/products")
    suspend fun getProductResponseList(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): List<ProductResponse>

    @GET("/products/categories/{id}")
    suspend fun getProductsByCategory(@Path("id") id: Long): List<ProductResponse>

    @GET("/products/categories")
    suspend fun getProductCategoriesResponse(): List<ProductCategoryResponse>
}