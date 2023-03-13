package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.restaurants.Building
import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.domain.model.restaurants.Restaurant
import com.example.hrautomation.domain.model.restaurants.Review

interface RestaurantsRepository {

    suspend fun getBuildingsByCity(cityId: Long): List<Building>

    suspend fun getCitiesResponse(): List<City>

    suspend fun getBuildingById(buildingId: Long): Building?

    suspend fun getRestaurantById(restaurantId: Long): Restaurant

    suspend fun getReviewsByRestaurantId(restaurantId: Long): List<Review>

    suspend fun addReview(restaurantId: Long, userId: Long, content: String, check: Int, rating: Float)

}