package com.example.hrautomation.domain.model.social.list_event

import java.util.Date

class ListEvent(
    val id: Long,
    val name: String,
    val date: Date,
    val pictureUrl: String?,
    val format: String
)