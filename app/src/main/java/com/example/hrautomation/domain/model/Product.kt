package com.example.hrautomation.domain.model

import com.example.hrautomation.presentation.model.ProductItem
import com.example.hrautomation.utils.IMapper

data class Product(
    val section: String,
    val img: String,
    val name: String
)

class ProductToProductItemMapper : IMapper<Product, ProductItem> {
    override fun convert(model: Product): ProductItem = ProductItem(model.section, model.img, model.name)
}