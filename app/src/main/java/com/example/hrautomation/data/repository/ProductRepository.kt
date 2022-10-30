package com.example.hrautomation.data.repository

import com.example.hrautomation.data.model.ProductResponse
import com.example.hrautomation.data.model.ProductResponseToProductMapper
import com.example.hrautomation.domain.model.ProductToListedProductItemMapper
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor() : IProductRepository {
    private val bread = ProductResponse("Хлебобулочные", "https://klike.net/uploads/posts/2020-09/1601201612_2.jpg", "Хлеб")
    private val pie = ProductResponse("Хлебобулочные", "https://1xleb.ru/upload/iblock/489/48942c2cb0eae6e117e03472cd34b8e3.jpg", "Пирожок")
    private val water =
        ProductResponse("Вода", "https://www.uavgusta.net/upload/resize_cache/iblock/d5c/740_740_2/15_faktov_o_roli_vody_v_zhizni_cheloveka.jpg", "Минералочка")
    private val productResponseList = listOf(bread, pie, water)

    // TODO: Make this method return domain model instead of presentation model
    override suspend fun getProductItemList(): List<BaseListItem> {
        return productResponseList.map {
            ProductToListedProductItemMapper().convert(
                ProductResponseToProductMapper().convert(it)
            )
        }
    }
}