package com.example.hrautomation.di

import com.example.hrautomation.data.api.*
import com.example.hrautomation.data.repository.TokenRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class RetrofitProvider @Inject constructor(tokenRepository: TokenRepository) {


    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenRepository.getToken() ?: "")).build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("http://localhost:8080")
        .addConverterFactory(GsonConverterFactory.create())

    val userApi: IUserApi = retrofitBuilder
        .client(httpClient)
        .build()
        .create()

    val userApi2: IIUserApi = IIUserApi()

    val employeesApi: IEmployeesApi = retrofitBuilder
        .client(httpClient)
        .build()
        .create()

    val employeesApi2: IIEmployeesApi = IIEmployeesApi()
}