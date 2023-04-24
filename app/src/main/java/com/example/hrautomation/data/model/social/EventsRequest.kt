package com.example.hrautomation.data.model.social

data class EventsRequest(
    val events: List<ListEventResponse>,
    val pages: Int
)