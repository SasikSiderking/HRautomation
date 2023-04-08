package com.example.hrautomation.domain.model.social

import java.util.*

class ListEvent(
    val id: Long,
    val name: String,
    val date: Date,
    val address: String,
    val pictureUrl: String?,
    val online: Boolean,
    val isOngoing: Boolean
)