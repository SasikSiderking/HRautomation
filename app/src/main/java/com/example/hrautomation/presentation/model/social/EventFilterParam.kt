package com.example.hrautomation.presentation.model.social

import java.io.Serializable
import java.util.*

data class EventFilterParam(
    val name: String?,
    val fromDate: Date?,
    val toDate: Date?,
    val cityId: Long?,
    val format: EventFormat?
) : Serializable