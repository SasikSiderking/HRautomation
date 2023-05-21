package com.example.hrautomation.data.model.social.list_event

import java.util.Date

class ListEventResponse(
    val id: Long,
    val name: String,
    val date: Date,
    val address: String,
    val pictureUrl: String?,
    val format: String
)