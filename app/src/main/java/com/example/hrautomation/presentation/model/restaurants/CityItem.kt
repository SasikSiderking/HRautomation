package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class CityItem(
    override val id: Long,
    val name: String,
    val lat: Double,
    val lng: Double
) : BaseListItem

class CityToCityItemMapper : Mapper<City, CityItem> {
    override fun convert(model: City): CityItem = CityItem(
        model.id,
        model.name,
        model.lat,
        model.lng
    )
}
