package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.social.EventsRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface SocialApi {
    @GET("/events/get")
    suspend fun getEvents(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String,
    ): EventsRequest
}