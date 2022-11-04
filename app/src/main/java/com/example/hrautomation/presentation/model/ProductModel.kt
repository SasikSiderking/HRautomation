package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.IMapper

data class ListedProductItem(
    val section: String,
    val img: String,
    val name: String,
    override val id: String = name
) : BaseListItem

class ProductToListedProductItemMapper : IMapper<Product, ListedProductItem> {
    override fun convert(model: Product): ListedProductItem =
        ListedProductItem(model.section, model.img, model.name)
}