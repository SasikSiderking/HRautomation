package com.example.hrautomation.presentation.model.social

import java.io.Serializable

data class EventFilterParam(
    val name: String?,
    val date: String?,
    val cityId: Int?,
    val format: String?
) : Serializable