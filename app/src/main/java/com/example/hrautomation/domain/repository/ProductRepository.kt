package com.example.hrautomation.domain.repository

import com.example.hrautomation.presentation.model.ProductItem

interface ProductRepository {
    suspend fun getProductItemList(): List<ProductItem>
}