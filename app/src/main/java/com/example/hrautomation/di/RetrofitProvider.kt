package com.example.hrautomation.di

import com.example.hrautomation.BuildConfig
import com.example.hrautomation.data.api.*
import com.example.hrautomation.data.repository.TokenRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class RetrofitProvider @Inject constructor(tokenRepositoryImpl: TokenRepositoryImpl) {


    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenRepositoryImpl.getToken() ?: "")).build()
    }

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val userApi: UserApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }

    val userApi2: UserApi2 = UserApi2()

    val employeesApi: EmployeesApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }

    val employeesApi2: EmployeesApi2 = EmployeesApi2()
}