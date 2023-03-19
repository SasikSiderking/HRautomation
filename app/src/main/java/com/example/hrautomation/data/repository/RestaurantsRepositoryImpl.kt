package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.RestaurantsApi
import com.example.hrautomation.data.model.restaurants.*
import com.example.hrautomation.domain.model.restaurants.*
import com.example.hrautomation.domain.repository.BuildingsCacheManager
import com.example.hrautomation.domain.repository.RestaurantsRepository
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsApi: RestaurantsApi,
    private val buildingsResponseToBuildingsMapper: BuildingsResponseToBuildingsMapper,
    private val cityResponseToCityMapper: CityResponseToCityMapper,
    private val buildingsCacheManager: BuildingsCacheManager,
    private val restaurantResponseToRestaurantMapper: RestaurantResponseToRestaurantMapper,
    private val reviewResponseToReviewMapper: ReviewResponseToReviewMapper
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

    override suspend fun getRestaurantById(restaurantId: Long): Restaurant {
        return restaurantResponseToRestaurantMapper.convert(restaurantsApi.getRestaurantById(restaurantId))
    }

    override suspend fun getReviewsByRestaurantId(restaurantId: Long): List<Review> {
        return restaurantsApi.getReviewsByRestaurantId(restaurantId).map { reviewResponseToReviewMapper.convert(it) }
    }

    override suspend fun addReview(restaurantReviewRequest: RestaurantReviewRequest) {
        restaurantsApi.addReview(
            restaurantReviewRequest.userId,
            restaurantReviewRequest.restaurantId,
            ReviewRequest(restaurantReviewRequest.content, restaurantReviewRequest.check, restaurantReviewRequest.rating)
        )
    }
}