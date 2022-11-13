package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.FaqCategoryResponse
import com.example.hrautomation.data.model.FaqQuestionResponse
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

    val q1 = FaqQuestionResponse(
        0,
        "Header0",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        0
    )
    val q2 = FaqQuestionResponse(
        1,
        "Header1",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        1
    )
    val q4 = FaqQuestionResponse(
        3,
        "Header3",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        1
    )
    val q3 = FaqQuestionResponse(
        2,
        "Header2",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        2
    )


    val listCat: List<FaqCategoryResponse> = listOf(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11)
    val listQ: List<FaqQuestionResponse> = listOf(q1, q2, q3, q4)
    override suspend fun getFaqCategoriesResponse(): Response<List<FaqCategoryResponse>> {
        return Response.success(listCat)
    }

    override suspend fun getFaqQuestionsResponse(id: Long): Response<List<FaqQuestionResponse>> {
        return Response.success(listQ.filter { it.categoryId == id })
    }
}