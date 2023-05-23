package com.example.hrautomation.presentation.model.social.filter

import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.presentation.view.social.filter.EventFormat
import java.io.Serializable
import java.util.Date

data class EventFilterParam(
    val name: String?,
    val fromDate: Date?,
    val toDate: Date?,
    val city: CityItem?,
    val format: EventFormat?
) : Serializable