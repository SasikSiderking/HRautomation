package com.example.hrautomation.data.repository

import com.example.hrautomation.data.model.restaurants.BuildingResponse
import com.example.hrautomation.data.model.restaurants.BuildingsResponseToBuildingsMapper
import com.example.hrautomation.domain.model.restaurants.Building
import com.example.hrautomation.domain.repository.CachedBuildingsRepository
import javax.inject.Inject

class CachedBuildingsRepositoryImpl @Inject constructor(private val buildingsResponseToBuildingsMapper: BuildingsResponseToBuildingsMapper) :
    CachedBuildingsRepository {

    private var buildings: List<BuildingResponse> = emptyList()

    override fun setBuildings(buildings: List<BuildingResponse>) {
        this.buildings = buildings
    }

    override fun getBuilding(id: Long): Building? {
        val building = buildings.find { it.id == id }
        return building?.let { buildingsResponseToBuildingsMapper.convert(it) }
    }

}