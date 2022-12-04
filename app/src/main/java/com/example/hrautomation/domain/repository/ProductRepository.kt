package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.model.ProductCategory
import com.example.hrautomation.domain.model.ProductSortBy


interface ProductRepository {

    suspend fun getProductList(pageNumber: Int, size: Int, sortBy: ProductSortBy): Result<List<Product>>

    suspend fun orderProduct(id: Long): Result<Unit>

    suspend fun getProductCategoryList(): Result<List<ProductCategory>>

    suspend fun getProductsByCategory(categoryId: Long): Result<List<Product>>
}