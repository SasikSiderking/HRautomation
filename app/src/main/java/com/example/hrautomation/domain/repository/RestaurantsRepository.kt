package com.example.hrautomation.domain.repository

import com.example.hrautomation.data.model.restaurants.Building
import com.example.hrautomation.domain.model.restaurants.City

interface RestaurantsRepository {

    suspend fun getBuildingsByCity(cityId: Long): List<Building>

    suspend fun getCitiesResponse(): List<City>
}