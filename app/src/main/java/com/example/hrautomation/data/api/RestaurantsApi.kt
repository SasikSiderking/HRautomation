package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.restaurants.BuildingResponse
import com.example.hrautomation.data.model.restaurants.CityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantsApi {
    @GET("/restaurants/get/city/{cityId}")
    suspend fun getBuildingsResponseByCity(@Path("cityId") cityId: Long): List<BuildingResponse>

    @GET("/cities/get/all")
    suspend fun getCitiesResponse(): List<CityResponse>
}