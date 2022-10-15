package com.example.hrautomation.data.repository

import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.repository.IProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(): IProductRepository {
    override suspend fun getProductList(): List<Product> {
        val bread = Product("Хлебобулочные", "https://klike.net/uploads/posts/2020-09/1601201612_2.jpg", "Хлеб")
        val pie = Product("Хлебобулочные", "https://1xleb.ru/upload/iblock/489/48942c2cb0eae6e117e03472cd34b8e3.jpg", "Пирожок")
        val water = Product("Вода", "https://www.uavgusta.net/upload/resize_cache/iblock/d5c/740_740_2/15_faktov_o_roli_vody_v_zhizni_cheloveka.jpg", "Минералочка")
        return mutableListOf(bread, pie, water)
    }



}