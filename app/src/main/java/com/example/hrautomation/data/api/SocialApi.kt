package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.social.EventFilter
import com.example.hrautomation.data.model.social.EventsRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface SocialApi {
    @POST("/events/get")
    suspend fun getEvents(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String,
        @Body filter: EventFilter
    ): EventsRequest
}