package com.example.hrautomation.di

import com.example.hrautomation.data.api.IEmployeesApi
import com.example.hrautomation.data.api.IIEmployeesApi
import com.example.hrautomation.data.api.IIUserApi
import com.example.hrautomation.data.api.IUserApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideUserApi(retrofitProvider: RetrofitProvider): IUserApi {
        return retrofitProvider.userApi
    }

    @Provides
    @Singleton
    fun provideUserApi2(retrofitProvider: RetrofitProvider): IIUserApi {
        return retrofitProvider.userApi2
    }

    @Provides
    @Singleton
    fun provideEmployeesApi(retrofitProvider: RetrofitProvider): IEmployeesApi {
        return retrofitProvider.employeesApi
    }

    @Provides
    @Singleton
    fun provideEmployeesApi2(retrofitProvider: RetrofitProvider): IIEmployeesApi {
        return retrofitProvider.employeesApi2
    }
}