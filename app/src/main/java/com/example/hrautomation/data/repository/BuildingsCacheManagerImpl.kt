package com.example.hrautomation.data.repository

import com.example.hrautomation.data.model.restaurants.BuildingResponse
import com.example.hrautomation.domain.repository.BuildingsCacheManager
import javax.inject.Inject

class BuildingsCacheManagerImpl @Inject constructor() : BuildingsCacheManager {

    private var buildings: List<BuildingResponse> = emptyList()

    override fun setBuildings(buildings: List<BuildingResponse>) {
        this.buildings = buildings
    }

    override fun getBuilding(id: Long): BuildingResponse? {
        return buildings.find { it.id == id }
    }

}