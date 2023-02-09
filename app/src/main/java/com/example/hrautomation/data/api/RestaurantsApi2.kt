package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.restaurants.CityResponse
import com.example.hrautomation.data.model.restaurants.ListRestaurantResponse

class RestaurantsApi2 : RestaurantsApi {

    val res1 = ListRestaurantResponse(
        0, "Rostovskaya Shaurma", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4587363, 84.9619166
    )

    val res2 = ListRestaurantResponse(
        1, "Rostovskaya Shaurma", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4635407, 84.9651108
    )

    val res3 = ListRestaurantResponse(
        2, "Rostovskaya Shaurma", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.5031006, 85.0098316
    )

    val res4 = ListRestaurantResponse(
        3, "Prokofe", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.477834, 84.9501566
    )

    val res5 = ListRestaurantResponse(
        4, "RE:BRO", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4770359, 84.9502384
    )

    val res6 = ListRestaurantResponse(
        5, "Дыхание вока", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4611903, 84.9503171
    )

    val res7 = ListRestaurantResponse(
        6, "Пешком постою", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4645867, 84.9538413
    )

    val res8 = ListRestaurantResponse(
        7, "Зеленая улица", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4642469, 84.9552428
    )

    val res9 = ListRestaurantResponse(
        8, "Южане", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4933379, 84.9453544
    )

    val res10 = ListRestaurantResponse(
        9, "Южане", "Учебная 15", "Забегаловка", 240,
        4.5F, 56.4561052, 84.9604764
    )

    val list = listOf(res1, res2, res3, res4, res5, res6, res7, res8, res9, res10)

    override suspend fun getListRestaurantResponse(
        pageNumber: Int,
        size: Int,
        sortBy: String
    ): List<ListRestaurantResponse> {
        return list
    }

    override suspend fun getCitiesResponse(): List<CityResponse> {
        val city1 = CityResponse(0, "Томск", 56.4884, 84.9480)
        val city2 = CityResponse(1, "Красноярск", 56.0153, 92.8932)
        val city3 = CityResponse(2, "Пермь", 58.0092, 56.2270)
        val city4 = CityResponse(3, "Екатеринбург", 56.8431, 60.6454)
        val city5 = CityResponse(4, "Уфа", 54.7348, 55.9579)
        return listOf(city1, city2, city3, city4, city5)
    }

}