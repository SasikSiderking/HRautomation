package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.restaurants.CityResponse
import com.example.hrautomation.data.model.restaurants.ListRestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApi {
    @GET("/restaurants/get/all")
    suspend fun getListRestaurantResponse(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): List<ListRestaurantResponse>

    @GET("/cities/get/all")
    suspend fun getCitiesResponse(): List<CityResponse>
}