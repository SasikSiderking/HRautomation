package com.example.hrautomation.domain.model

import com.example.hrautomation.presentation.model.ListedProductItem
import com.example.hrautomation.utils.IMapper

data class Product(
    val section: String,
    val img: String,
    val name: String
)

class ProductToListedProductItemMapper : IMapper<Product, ListedProductItem> {
    override fun convert(model: Product): ListedProductItem =
        ListedProductItem(model.section, model.img, model.name)
}