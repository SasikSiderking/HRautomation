package com.example.hrautomation.data.model.restaurants

import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.utils.Mapper

data class ListRestaurantResponse(
    val id: Long,
    val name: String,
    val address: String,
    val status: RestaurantStatusResponse,
    val check: Float,
    val rating: Float,
    val lat: Double,
    val lng: Double
)

class ListRestaurantResponseToListRestaurantMapper : Mapper<ListRestaurantResponse, ListRestaurant> {

    override fun convert(model: ListRestaurantResponse): ListRestaurant =
        ListRestaurant(
            model.id,
            model.name,
            model.address,
            model.status,
            model.check,
            model.rating,
            model.lat,
            model.lng
        )

}