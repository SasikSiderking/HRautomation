package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.EmployeeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeesApi {
    @GET("/users")
    suspend fun getEmployeesResponse(): Response<List<EmployeeResponse>>

    @GET("/users/{id}")
    suspend fun getEmployeeResponse(@Path("id") id: Long): Response<EmployeeResponse>
}