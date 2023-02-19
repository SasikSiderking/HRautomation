package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.RestaurantsApi
import com.example.hrautomation.data.model.restaurants.CityResponseToCityMapper
import com.example.hrautomation.data.model.restaurants.ListRestaurantResponseToListRestaurantMapper
import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.domain.model.restaurants.RestaurantSortBy
import com.example.hrautomation.domain.repository.RestaurantsRepository
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsApi: RestaurantsApi,
    private val listRestaurantResponseToListRestaurantMapper: ListRestaurantResponseToListRestaurantMapper,
    private val cityResponseToCityMapper: CityResponseToCityMapper
) : RestaurantsRepository {

    override suspend fun getBuildingsByCity(pageNumber: Int, size: Int, sortBy: RestaurantSortBy): List<ListRestaurant> {
        return restaurantsApi.getListRestaurantResponse(pageNumber, size, sortBy.sortBy)
            .map { listRestaurantResponseToListRestaurantMapper.convert(it) }
    }

    override suspend fun getCitiesResponse(): List<City> {
        return restaurantsApi.getCitiesResponse().map { cityResponseToCityMapper.convert(it) }
    }
}