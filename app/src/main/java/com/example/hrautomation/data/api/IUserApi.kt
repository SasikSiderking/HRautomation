package com.example.hrautomation.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IUserApi {
    @GET("/authorization")
    suspend fun checkEmail(@Query("email") email: String): Response<Boolean>

    @GET("/authorization/confirm")
    fun confirmEmail(@Query("email") email: String,@Query("code") code: String): Response<String>
}