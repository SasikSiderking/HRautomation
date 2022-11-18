package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Product


interface ProductRepository {

    suspend fun getProductItemList(pageNumber: Int, size: Int, sortBy: String): Result<List<Product>>

    suspend fun orderProduct(id: Long): Result<Boolean>
}