package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.SocialApi
import com.example.hrautomation.data.model.social.event.toEvent
import com.example.hrautomation.data.model.social.filter.toEventFilter
import com.example.hrautomation.data.model.social.list_event.toListEvent
import com.example.hrautomation.domain.model.social.event.Event
import com.example.hrautomation.domain.model.social.filter.Filter
import com.example.hrautomation.domain.model.social.list_event.EventSortBy
import com.example.hrautomation.domain.model.social.list_event.ListEvent
import com.example.hrautomation.domain.repository.SocialRepository
import javax.inject.Inject

class SocialRepositoryImpl @Inject constructor(private val socialApi: SocialApi) : SocialRepository {
    override suspend fun getAllEvents(pageNumber: Int, size: Int, sortBy: EventSortBy, filter: Filter): List<ListEvent> {
        return socialApi.getEvents(pageNumber, size, sortBy.sortBy, filter.toEventFilter()).events.map {
            it.toListEvent()
        }
    }

    override suspend fun getEventById(id: Long): Event {
        return socialApi.getEvent(id).toEvent()
    }
}