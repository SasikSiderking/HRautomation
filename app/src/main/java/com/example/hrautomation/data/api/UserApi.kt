package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/authorization")
    suspend fun checkEmail(@Query("email") email: String)

    @GET("/authorization/confirm")
    suspend fun confirmEmail(@Query("email") email: String, @Query("code") code: String): Response<TokenResponse>

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id: Long): Response<EmployeeResponse>

    @PUT("/users")
    suspend fun saveUser(@Body user: EmployeeResponse)
}