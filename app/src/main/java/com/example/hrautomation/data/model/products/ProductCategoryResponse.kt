package com.example.hrautomation.data.model.products

import com.example.hrautomation.domain.model.products.ProductCategory
import com.example.hrautomation.utils.Mapper

data class ProductCategoryResponse(
    val id: Long,
    val name: String
)

class ProductCategoryResponseToProductCategoryMapper : Mapper<ProductCategoryResponse, ProductCategory> {
    override fun convert(model: ProductCategoryResponse): ProductCategory = ProductCategory(model.id, model.name)
}