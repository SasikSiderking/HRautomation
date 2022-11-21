package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.ProductCategoryResponse
import com.example.hrautomation.data.model.ProductResponse
import retrofit2.Response

class ProductApi2 : ProductApi {
    private val bread = ProductResponse(0, 0, "https://klike.net/uploads/posts/2020-09/1601201612_2.jpg", "Хлеб")
    private val pie = ProductResponse(1, 0, "https://1xleb.ru/upload/iblock/489/48942c2cb0eae6e117e03472cd34b8e3.jpg", "Пирожок")
    private val water =
        ProductResponse(
            2,
            1,
            "https://www.uavgusta.net/upload/resize_cache/iblock/d5c/740_740_2/15_faktov_o_roli_vody_v_zhizni_cheloveka.jpg",
            "Минералочка"
        )
    private val productResponseList = listOf(bread, pie, pie, water)

    override suspend fun orderProduct(id: Long): Response<Boolean> {
        return Response.success(true)
    }

    override suspend fun getProductResponseList(pageNumber: Int, size: Int, sortBy: String): Response<List<ProductResponse>> {
        return Response.success(productResponseList)
    }

    override suspend fun getProductsByCategory(id: Long): Response<List<ProductResponse>> {
        return Response.success(productResponseList.filter { it.categoryId == id })
    }

    override suspend fun getProductCategoriesResponse(): Response<List<ProductCategoryResponse>> {
        val cat1 = ProductCategoryResponse(0, "Выпечка")
        val cat2 = ProductCategoryResponse(1, "Вода")
        val cat3 = ProductCategoryResponse(2, "Молочка")
        val cat4 = ProductCategoryResponse(3, "Фрукты")
        val cat5 = ProductCategoryResponse(4, "Чипсеки")
        val cat6 = ProductCategoryResponse(5, "Другое")
        return Response.success(listOf(cat1, cat2, cat3, cat4, cat5, cat6))
    }
}