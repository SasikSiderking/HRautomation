package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.data.model.restaurants.RestaurantStatusResponse
import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ListRestaurantItem(
    override val id: Long,
    val name: String,
    val address: String,
    val status: RestaurantStatusResponse,
    val check: Float,
    val rating: Float,
    val lat: Double,
    val lng: Double
) : BaseListItem

class ListRestaurantToListRestaurantItemMapper : Mapper<ListRestaurant, ListRestaurantItem> {

    override fun convert(model: ListRestaurant): ListRestaurantItem =
        ListRestaurantItem(
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