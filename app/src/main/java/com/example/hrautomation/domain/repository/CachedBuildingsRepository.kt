package com.example.hrautomation.domain.repository

import com.example.hrautomation.data.model.restaurants.BuildingResponse
import com.example.hrautomation.domain.model.restaurants.Building

interface CachedBuildingsRepository {
    fun setBuildings(buildings: List<BuildingResponse>)

    fun getBuilding(id: Long): Building?
}