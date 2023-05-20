package com.example.hrautomation.data.model.restaurants

import com.example.hrautomation.domain.model.restaurants.Restaurant
import com.example.hrautomation.utils.Mapper
import javax.inject.Inject

data class RestaurantResponse(
    val id: Long,
    val name: String?,
    val rating: Float,
    val status: String?,
    val average: Int,
    val address: String?
)

class RestaurantResponseToRestaurantMapper @Inject constructor() :
    Mapper<RestaurantResponse, Restaurant> {
    override fun convert(model: RestaurantResponse): Restaurant {
        return Restaurant(
            model.id,
            model.name ?: "",
            model.rating,
            model.status ?: "",
            model.average,
            model.address ?: ""
        )
    }
}