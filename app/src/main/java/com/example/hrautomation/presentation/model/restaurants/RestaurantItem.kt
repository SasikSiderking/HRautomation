package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.R
import com.example.hrautomation.domain.model.restaurants.Restaurant
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper
import com.example.hrautomation.utils.resources.StringResourceProvider
import javax.inject.Inject
import kotlin.math.floor

data class RestaurantItem(
    override val id: Long,
    val name: String,
    val rating: String,
    val status: String,
    val check: String,
    val address: String
) : BaseListItem

class RestaurantToRestaurantItemMapper @Inject constructor(private val stringResourceProvider: StringResourceProvider) :
    Mapper<Restaurant, RestaurantItem> {
    override fun convert(model: Restaurant): RestaurantItem {
        return RestaurantItem(
            model.id,
            stringResourceProvider.getString(R.string.restaurant_item_name, model.name),
            (floor(model.rating * 10.0) / 10.0).toString(),
            model.status,
            stringResourceProvider.getString(R.string.restaurnt_item_check, model.check),
            model.address
        )
    }

}