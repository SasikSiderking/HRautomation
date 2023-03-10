package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.restaurants.BuildingResponse
import com.example.hrautomation.data.model.restaurants.CityResponse
import com.example.hrautomation.data.model.restaurants.RestaurantResponse
import com.example.hrautomation.data.model.restaurants.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantsApi {
    @GET("/restaurants/get/city/{cityId}")
    suspend fun getBuildingsResponseByCity(@Path("cityId") cityId: Long): List<BuildingResponse>

    @GET("/cities/get/all")
    suspend fun getCitiesResponse(): List<CityResponse>

    @GET("restaurants/{restaurantId}")
    suspend fun getRestaurantById(@Path("restaurantId") restaurantId: Long): RestaurantResponse

    @GET("/reviews/get/restaurant/{restaurantId}")
    suspend fun getReviewsByRestaurantId(@Path("restaurantId") restaurantId: Long): List<ReviewResponse>
}