package com.example.hrautomation.domain.model

data class Product(
    val id: Long,
    val categoryId: Long,
    val img: String,
    val name: String
)