package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.social.event.EventResponse
import com.example.hrautomation.data.model.social.filter.EventFilter
import com.example.hrautomation.data.model.social.list_event.EventsRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SocialApi {
    @POST("/events/get")
    suspend fun getEvents(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String,
        @Body filter: EventFilter
    ): EventsRequest

    @GET
    suspend fun getEvent(@Path("id") id: Long): EventResponse
}