package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.presentation.model.restaurants.CityItem
import java.io.Serializable
import java.util.*

data class EventFilterParam(
    val name: String?,
    val fromDate: Date?,
    val toDate: Date?,
    val city: CityItem?,
    val format: EventFormat?
) : Serializable