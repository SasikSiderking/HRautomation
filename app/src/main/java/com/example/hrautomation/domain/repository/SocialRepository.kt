package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.social.EventSortBy
import com.example.hrautomation.domain.model.social.Filter
import com.example.hrautomation.domain.model.social.ListEvent

interface SocialRepository {
    suspend fun getAllEvents(pageNumber: Int, size: Int, sortBy: EventSortBy, filter: Filter): List<ListEvent>
}