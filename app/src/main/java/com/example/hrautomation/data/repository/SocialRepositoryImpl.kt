package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.SocialApi
import com.example.hrautomation.data.model.social.toListEvent
import com.example.hrautomation.domain.model.social.EventSortBy
import com.example.hrautomation.domain.model.social.ListEvent
import com.example.hrautomation.domain.repository.SocialRepository
import javax.inject.Inject

class SocialRepositoryImpl @Inject constructor(private val socialApi: SocialApi) : SocialRepository {
    override suspend fun getAllEvents(pageNumber: Int, size: Int, sortBy: EventSortBy): List<ListEvent> {
        return socialApi.getEvents(pageNumber, size, sortBy.sortBy).map {
            it.toListEvent()
        }
    }
}