package com.example.hrautomation.data.model.social.list_event

data class EventsRequest(
    val events: List<ListEventResponse>,
    val pages: Int
)