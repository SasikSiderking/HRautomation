package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.model.ProductCategory
import com.example.hrautomation.domain.model.ProductSortBy


interface ProductRepository {

    suspend fun getProductList(pageNumber: Int, size: Int, sortBy: ProductSortBy): List<Product>

    suspend fun orderProduct(id: Long)

    suspend fun getProductCategoryList(): List<ProductCategory>

    suspend fun getProductsByCategory(categoryId: Long): List<Product>
}