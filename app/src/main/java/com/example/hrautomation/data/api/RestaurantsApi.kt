package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.restaurants.CityResponse
import com.example.hrautomation.data.model.restaurants.ListRestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantsApi {
    @GET("/restaurants/get/city/{cityId}")
    suspend fun getBuildingsResponseByCity(@Path("cityId") cityId: Long): List<ListRestaurantResponse>

    @GET("/cities/get/all")
    suspend fun getCitiesResponse(): List<CityResponse>
}