package com.example.hrautomation.presentation.model.restaurants

import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.utils.Mapper

data class CityItem(
    val id: Long,
    val name: String
)

class CityToCityItemMapper : Mapper<City, CityItem> {
    override fun convert(model: City): CityItem = CityItem(
        model.id,
        model.name
    )
}
