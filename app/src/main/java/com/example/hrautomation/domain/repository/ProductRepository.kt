package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.model.ProductCategory


interface ProductRepository {

    suspend fun getProductList(pageNumber: Int, size: Int, sortBy: String): Result<List<Product>>

    suspend fun orderProduct(id: Long): Result<Boolean>

    suspend fun getProductCategoryList(): Result<List<ProductCategory>>
}