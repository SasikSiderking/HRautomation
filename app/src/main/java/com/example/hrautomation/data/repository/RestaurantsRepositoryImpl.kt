package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.RestaurantsApi
import com.example.hrautomation.data.model.restaurants.BuildingsResponseToBuildingsMapper
import com.example.hrautomation.data.model.restaurants.CityResponseToCityMapper
import com.example.hrautomation.domain.model.restaurants.Building
import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.domain.repository.BuildingsCacheManager
import com.example.hrautomation.domain.repository.RestaurantsRepository
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsApi: RestaurantsApi,
    private val buildingsResponseToBuildingsMapper: BuildingsResponseToBuildingsMapper,
    private val cityResponseToCityMapper: CityResponseToCityMapper,
    private val buildingsCacheManager: BuildingsCacheManager
) : RestaurantsRepository {

    override suspend fun getBuildingsByCity(cityId: Long): List<Building> {
        val buildingsResponse = restaurantsApi.getBuildingsResponseByCity(cityId)
        buildingsCacheManager.setBuildings(buildingsResponse)
        return buildingsResponse.map { buildingsResponseToBuildingsMapper.convert(it) }
    }

    override suspend fun getCitiesResponse(): List<City> {
        return restaurantsApi.getCitiesResponse().map { cityResponseToCityMapper.convert(it) }
    }

    override suspend fun getBuildingById(buildingId: Long): Building? {
        val buildingResponse = buildingsCacheManager.getBuilding(buildingId)
        return buildingResponse?.let { buildingsResponseToBuildingsMapper.convert(it) }
    }
}