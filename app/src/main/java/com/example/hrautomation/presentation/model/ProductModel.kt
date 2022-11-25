package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ProductItem(
    override val id: Long,
    val categoryId: Long,
    val img: String,
    val name: String
) : BaseListItem

class ProductToListedProductItemMapper : Mapper<Product, ProductItem> {
    override fun convert(model: Product): ProductItem =
        ProductItem(model.id, model.categoryId, model.img, model.name)
}