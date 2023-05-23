package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.social.event.Event
import com.example.hrautomation.domain.model.social.filter.Filter
import com.example.hrautomation.domain.model.social.list_event.EventSortBy
import com.example.hrautomation.domain.model.social.list_event.ListEvent

interface SocialRepository {
    suspend fun getAllEvents(pageNumber: Int, size: Int, sortBy: EventSortBy, filter: Filter): List<ListEvent>

    suspend fun getEventById(id: Long): Event
}