package com.example.hrautomation.presentation.model.products

import com.example.hrautomation.domain.model.products.ProductCategory
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ProductCategoryItem(
    override val id: Long,
    val name: String
) : BaseListItem

class ProductCategoryToProductCategoryItemMapper : Mapper<ProductCategory, ProductCategoryItem> {
    override fun convert(model: ProductCategory): ProductCategoryItem = ProductCategoryItem(model.id, model.name)
}