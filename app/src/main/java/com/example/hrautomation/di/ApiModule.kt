package com.example.hrautomation.di

import com.example.hrautomation.data.api.IEmployeesApi
import com.example.hrautomation.data.api.IIEmployeesApi
import com.example.hrautomation.data.api.IIUserApi
import com.example.hrautomation.data.api.IUserApi
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

class ApiModule {

    @Provides
    @Singleton
    fun provideUserApi(retrofitBuilder: Retrofit.Builder): IUserApi {
        return retrofitBuilder
            .build()
            .create(IUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi2(): IIUserApi {
        return IIUserApi()
    }

    @Provides
    @Singleton
    fun provideEmployeesApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): IEmployeesApi {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(IEmployeesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEmployeesApi2(): IIEmployeesApi {
        return IIEmployeesApi()
    }
}