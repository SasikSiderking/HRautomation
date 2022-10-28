package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.utils.Mapper

data class ProductItem(
    val section: String,
    val img: String,
    val name: String
)

class ProductToProductItemMapper : Mapper<Product, ProductItem> {
    override fun convert(model: Product): ProductItem = ProductItem(model.section, model.img, model.name)
}