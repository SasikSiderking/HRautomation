package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("/authorization")
    suspend fun checkEmail(@Query("email") email: String): Response<Boolean>

    @GET("/authorization/confirm")
    suspend fun confirmEmail(@Query("email") email: String, @Query("code") code: String): Response<TokenResponse>
}