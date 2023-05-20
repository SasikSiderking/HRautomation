package com.example.hrautomation.data.model.restaurants

import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.utils.Mapper

data class CityResponse(
    val id: Long,
    val name: String?,
    val lat: Double,
    val lng: Double
)

class CityResponseToCityMapper : Mapper<CityResponse, City> {
    override fun convert(model: CityResponse): City = City(
        model.id,
        model.name ?: "",
        model.lat,
        model.lng
    )

}
