package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.employee.EmployeeResponse
import com.example.hrautomation.data.model.employee.GetEmployees
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EmployeesApi {
    @GET("/users")
    suspend fun getEmployeesResponse(
        @Query("pageNumber") pageNumber: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String
    ): GetEmployees

    @GET("/users/{id}")
    suspend fun getEmployeeResponse(@Path("id") id: Long): EmployeeResponse
}