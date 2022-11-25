package com.example.hrautomation.data.model

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.utils.Mapper

data class ProductResponse(
    val id: Long,
    val categoryId: Long,
    val img: String,
    val name: String
)

class ProductResponseToProductMapper : Mapper<ProductResponse, Product> {
    override fun convert(model: ProductResponse): Product = Product(model.id, model.categoryId, model.img, model.name)
}