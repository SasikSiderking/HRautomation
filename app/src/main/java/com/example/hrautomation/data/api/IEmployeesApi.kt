package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.EmployeeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IEmployeesApi {
    @GET("/users")
    suspend fun getEmployeesResponse(): Response<List<EmployeeResponse>>

    @GET("/users/{id}")
    suspend fun getEmployeeResponse(@Path("id") id: Int): Response<EmployeeResponse>
}