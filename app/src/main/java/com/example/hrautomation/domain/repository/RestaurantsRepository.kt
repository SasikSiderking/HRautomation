package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.domain.model.restaurants.RestaurantSortBy

interface RestaurantsRepository {

    suspend fun getRestaurantList(pageNumber: Int, size: Int, sortBy: RestaurantSortBy): List<ListRestaurant>

}