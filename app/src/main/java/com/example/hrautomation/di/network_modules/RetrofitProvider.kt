package com.example.hrautomation.di.network_modules

import com.example.hrautomation.BuildConfig
import com.example.hrautomation.data.api.*
import com.example.hrautomation.data.repository.TokenRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class RetrofitProvider @Inject constructor(private val tokenRepositoryImpl: TokenRepositoryImpl) {


    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenRepositoryImpl.getToken() ?: ""))
            .addInterceptor(logging)
            .build()
    }


    private val logging: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
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

    val userApi2: UserApi2 by lazy { UserApi2() }

    val employeesApi: EmployeesApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }

    val employeesApi2: EmployeesApi2 by lazy { EmployeesApi2() }
}