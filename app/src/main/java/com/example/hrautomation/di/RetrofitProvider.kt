package com.example.hrautomation.di

import com.example.hrautomation.BuildConfig
import com.example.hrautomation.data.api.*
import com.example.hrautomation.data.repository.TokenRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class RetrofitProvider @Inject constructor(tokenRepository: TokenRepository) {


    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenRepository.getToken() ?: "")).build()
    }

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val userApi: IUserApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }

    val userApi2: IIUserApi = IIUserApi()

    val employeesApi: IEmployeesApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }

    val employeesApi2: IIEmployeesApi = IIEmployeesApi()
}