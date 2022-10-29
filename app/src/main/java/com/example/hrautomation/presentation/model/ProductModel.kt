package com.example.hrautomation.presentation.model

interface ProductItem {
    val section: String
    val img: String
    val name: String
}

data class ListedProductItem(
    override val section: String,
    override val img: String,
    override val name: String
) : ProductItem