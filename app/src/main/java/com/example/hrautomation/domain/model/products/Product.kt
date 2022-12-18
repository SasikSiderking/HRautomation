package com.example.hrautomation.domain.model.products

data class Product(
    val id: Long,
    val categoryId: Long,
    val pictureUrl: String?,
    val name: String
)