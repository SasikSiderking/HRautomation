package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.social.ListEventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SocialApi {
    @GET("/events/get/archive")
    suspend fun getPastEvents(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): List<ListEventResponse>

    @GET("events/get/current")
    suspend fun getCurrentEvents(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): List<ListEventResponse>
}