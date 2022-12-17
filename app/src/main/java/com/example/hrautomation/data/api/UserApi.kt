package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.employee.EmployeeResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApi {

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id: Long): EmployeeResponse

    @PUT("/users")
    suspend fun saveUser(@Body user: EmployeeResponse)

    @Multipart
    @POST("/file/user/{id}")
    suspend fun uploadProfileImage(@Path("id") id: Long, @Part file: MultipartBody.Part)
}