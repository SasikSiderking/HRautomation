package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ListedProductItem(
    override val id: Long,
    val section: String,
    val img: String,
    val name: String
) : BaseListItem

class ProductToListedProductItemMapper : Mapper<Product, ListedProductItem> {
    override fun convert(model: Product): ListedProductItem =
        ListedProductItem(model.id, model.section, model.img, model.name)
}