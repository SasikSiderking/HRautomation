package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.R
import com.example.hrautomation.domain.model.restaurants.Building
import com.example.hrautomation.domain.model.restaurants.ListRestaurant
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper
import com.example.hrautomation.utils.resources.StringResourceProvider
import com.example.hrautomation.utils.restaurants.RestaurantUtils
import javax.inject.Inject

data class BuildingItem(
    override val id: Long,
    val address: String,
    val lat: Double,
    val lng: Double,
    val restaurants: List<ListRestaurantItem>
) : BaseListItem

data class ListRestaurantItem(
    override val id: Long,
    val name: String,
    val address: String,
    val statusAndCheck: String,
    val rating: String,
) : BaseListItem

class ListRestaurantToListRestaurantItemMapper(private val stringResourceProvider: StringResourceProvider) :
    Mapper<ListRestaurant, ListRestaurantItem> {

    override fun convert(model: ListRestaurant): ListRestaurantItem =
        ListRestaurantItem(
            model.id,
            model.name,
            model.address,
            stringResourceProvider.getString(R.string.restaurants_status_check, model.status, model.check),
            RestaurantUtils.roundRating(model.rating)
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