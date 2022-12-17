package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.products.Product
import com.example.hrautomation.domain.model.products.ProductCategory
import com.example.hrautomation.domain.model.products.ProductSortBy


interface ProductRepository {

    suspend fun getProductList(pageNumber: Int, size: Int, sortBy: ProductSortBy): List<Product>

    suspend fun orderProduct(id: Long)

    suspend fun getProductCategoryList(): List<ProductCategory>

    suspend fun getProductsByCategory(categoryId: Long): List<Product>
}