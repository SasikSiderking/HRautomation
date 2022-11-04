package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Product


interface IProductRepository {

    suspend fun getProductItemList(): List<Product>
}