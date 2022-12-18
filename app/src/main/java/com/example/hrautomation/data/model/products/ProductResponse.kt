package com.example.hrautomation.data.model.products

import com.example.hrautomation.domain.model.products.Product
import com.example.hrautomation.utils.Mapper

data class ProductResponse(
    val id: Long,
    val categoryId: Long,
    val pictureUrl: String?,
    val name: String
)

class ProductResponseToProductMapper : Mapper<ProductResponse, Product> {
    override fun convert(model: ProductResponse): Product =
        Product(model.id, model.categoryId, model.pictureUrl, model.name)
}