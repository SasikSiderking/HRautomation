package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.domain.model.restaurants.Building
import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper
import javax.inject.Inject

data class BuildingItem(
    val id: Long,
    val address: String,
    val lat: Double,
    val lng: Double,
    val restaurants: List<ListRestaurantItem>
)

data class ListRestaurantItem(
    override val id: Long,
    val name: String,
    val address: String,
    val statusAndCheck: String,
    val rating: Float,
) : BaseListItem

class ListRestaurantToListRestaurantItemMapper : Mapper<ListRestaurant, ListRestaurantItem> {

    override fun convert(model: ListRestaurant): ListRestaurantItem =
        ListRestaurantItem(
            model.id,
            model.name,
            model.address,
            "${model.status} · ${model.check}",
            model.rating
        )

}

class BuildingToBuildingItemMapper @Inject constructor(private val listRestaurantToListRestaurantItemMapper: ListRestaurantToListRestaurantItemMapper) :
    Mapper<Building, BuildingItem> {
    override fun convert(model: Building): BuildingItem {
        return BuildingItem(
            model.id,
            model.address,
            model.lat,
            model.lng,
            model.restaurants.map { listRestaurantToListRestaurantItemMapper.convert(it) }
        )
    }

}