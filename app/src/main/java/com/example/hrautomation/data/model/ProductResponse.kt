package com.example.hrautomation.data.model

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.utils.IMapper

data class ProductResponse(
    val section: String,
    val img: String,
    val name: String
)

class ProductResponseToProductMapper : IMapper<ProductResponse, Product> {
    override fun convert(model: ProductResponse): Product = Product(model.section, model.img, model.name)
}