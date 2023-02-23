package com.example.hrautomation.data.model.restaurants

import com.example.hrautomation.domain.model.restaurants.Building
import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.utils.Mapper
import javax.inject.Inject

data class BuildingResponse(
    val id: Long,
    val address: String,
    val lat: Double,
    val lng: Double,
    val restaurants: List<ListRestaurantResponse>
)

data class ListRestaurantResponse(
    val id: Long,
    val name: String,
    val status: String,
    val average: Int,
    val rating: Float,
    val address: String
)

class ListRestaurantResponseToListRestaurantMapper : Mapper<ListRestaurantResponse, ListRestaurant> {

    override fun convert(model: ListRestaurantResponse): ListRestaurant =
        ListRestaurant(
            model.id,
            model.name,
            model.status,
            model.average,
            model.rating,
            model.address
        )

}

class BuildingsResponseToBuildingsMapper @Inject constructor(private val listRestaurantResponseToListRestaurantMapper: ListRestaurantResponseToListRestaurantMapper) :
    Mapper<BuildingResponse, Building> {
    override fun convert(model: BuildingResponse): Building {
        return Building(
            model.id,
            model.address,
            model.lat,
            model.lng,
            model.restaurants.map { listRestaurantResponseToListRestaurantMapper.convert(it) }
        )
    }
}