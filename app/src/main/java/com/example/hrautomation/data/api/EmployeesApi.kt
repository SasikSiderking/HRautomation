package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.employee.EmployeeResponse
import com.example.hrautomation.data.model.employee.ListEmployeeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EmployeesApi {
    @GET("/users")
    suspend fun getEmployeesResponse(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): Response<List<ListEmployeeResponse>>

    @GET("/users/{id}")
    suspend fun getEmployeeResponse(@Path("id") id: Long): Response<EmployeeResponse>
}