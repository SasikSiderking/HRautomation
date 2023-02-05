package com.example.hrautomation.data.model.restaurants

import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.utils.Mapper

data class CityResponse(
    val id: Long,
    val name: String
)

class CityResponseToCityMapper : Mapper<CityResponse, City> {
    override fun convert(model: CityResponse): City = City(
        model.id,
        model.name
    )

}
