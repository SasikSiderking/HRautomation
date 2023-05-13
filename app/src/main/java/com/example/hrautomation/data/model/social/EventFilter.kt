package com.example.hrautomation.data.model.social

import java.util.Date

data class EventFilter(
    val name: String?,
    val fromDate: Date?,
    val toDate: Date?,
    val cityId: Long?,
    val format: String?
)