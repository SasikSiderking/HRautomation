package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.FaqCategoryResponse
import retrofit2.Response

class FaqApi2 : FaqApi {
    val cat1 = FaqCategoryResponse(0, "Что вершит судьбу человечества в этом мире?")
    val cat2 = FaqCategoryResponse(1, "Некое незримое существо или закон,")
    val cat3 = FaqCategoryResponse(2, " подобно Длани Господней парящей над миром?")
    val cat4 = FaqCategoryResponse(3, "Один раз забудешь")
    val cat5 = FaqCategoryResponse(4, "Запомнят")
    val cat6 = FaqCategoryResponse(5, "Один раз запомнишь")
    val cat7 = FaqCategoryResponse(6, "Забудут")
    val cat8 = FaqCategoryResponse(7, "Around the world")
    val cat9 = FaqCategoryResponse(8, "Around the world")
    val cat10 = FaqCategoryResponse(9, "Around the world")
    val cat11 = FaqCategoryResponse(10, "Around the world")


    val list: List<FaqCategoryResponse> = listOf(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11)
    override suspend fun getFaqCategoriesResponse(): Response<List<FaqCategoryResponse>> {
        return Response.success(list)
    }
}