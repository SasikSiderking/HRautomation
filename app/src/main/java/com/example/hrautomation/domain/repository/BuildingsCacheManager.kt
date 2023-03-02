package com.example.hrautomation.domain.repository

import com.example.hrautomation.data.model.restaurants.BuildingResponse

interface BuildingsCacheManager {
    fun setBuildings(buildings: List<BuildingResponse>)

    fun getBuilding(id: Long): BuildingResponse?
}