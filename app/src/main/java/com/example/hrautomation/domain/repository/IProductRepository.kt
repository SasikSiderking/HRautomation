package com.example.hrautomation.domain.repository

import com.example.hrautomation.presentation.model.ProductItem

interface IProductRepository {
    suspend fun getProductItemList(): List<ProductItem>
}