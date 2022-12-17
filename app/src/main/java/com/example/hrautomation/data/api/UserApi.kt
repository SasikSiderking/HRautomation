package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.employee.EmployeeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id: Long): EmployeeResponse

    @PUT("/users")
    suspend fun saveUser(@Body user: EmployeeResponse)
}