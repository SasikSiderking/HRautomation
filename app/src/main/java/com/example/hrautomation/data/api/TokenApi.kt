package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.tokens.NotificationTokenRequest
import com.example.hrautomation.data.model.tokens.TokenResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {
    @GET("/authorization")
    suspend fun checkEmail(@Query("email") email: String)

    @GET("/authorization/confirm")
    suspend fun confirmEmail(@Query("email") email: String, @Query("code") code: String): TokenResponse

    @POST("/refresh")
    suspend fun refreshToken(@Body refreshToken: RequestBody): TokenResponse

    @POST("/authorization/token")
    suspend fun sendNotificationToken(@Body notificationTokenRequest: NotificationTokenRequest)
}