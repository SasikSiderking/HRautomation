package com.example.hrautomation.domain.model.social.filter

import java.util.Date

data class Filter(
    val name: String?,
    val fromDate: Date?,
    val toDate: Date?,
    val cityId: Long?,
    val format: String?
)