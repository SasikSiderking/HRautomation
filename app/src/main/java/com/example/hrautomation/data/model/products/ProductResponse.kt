package com.example.hrautomation.data.model.products

import com.example.hrautomation.domain.model.products.Product
import com.example.hrautomation.utils.Mapper

data class ProductResponse(
    val id: Long,
    val categoryId: Long,
    val img: String?,
    val name: String
)

class ProductResponseToProductMapper : Mapper<ProductResponse, Product> {
    override fun convert(model: ProductResponse): Product =
        Product(model.id, model.categoryId, model.img ?: "https://cdn1.ozone.ru/s3/multimedia-6/c1000/6255833322.jpg", model.name)
}