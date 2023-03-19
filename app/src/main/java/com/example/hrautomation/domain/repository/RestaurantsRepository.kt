package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.restaurants.*

interface RestaurantsRepository {

    suspend fun getBuildingsByCity(cityId: Long): List<Building>

    suspend fun getCitiesResponse(): List<City>

    suspend fun getBuildingById(buildingId: Long): Building?

    suspend fun getRestaurantById(restaurantId: Long): Restaurant

    suspend fun getReviewsByRestaurantId(restaurantId: Long): List<Review>

    suspend fun addReview(restaurantReviewRequest: RestaurantReviewRequest)

}