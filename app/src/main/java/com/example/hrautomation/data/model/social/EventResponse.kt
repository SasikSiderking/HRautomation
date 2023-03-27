package com.example.hrautomation.data.model.social

import java.util.*

data class EventResponse(
    val id: Long,
    val name: String,
    val description: String,
    val date: Date,
    val address: String,
    val online: Boolean,
    val pictureUrl: String
)