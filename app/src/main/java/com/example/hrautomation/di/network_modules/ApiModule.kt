package com.example.hrautomation.di.network_modules

import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.api.EmployeesApi2
import com.example.hrautomation.data.api.UserApi
import com.example.hrautomation.data.api.UserApi2
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideUserApi(retrofitProvider: RetrofitProvider): UserApi {
        return retrofitProvider.userApi
    }

    @Provides
    @Singleton
    fun provideUserApi2(retrofitProvider: RetrofitProvider): UserApi2 {
        return retrofitProvider.userApi2
    }

    @Provides
    @Singleton
    fun provideEmployeesApi(retrofitProvider: RetrofitProvider): EmployeesApi {
        return retrofitProvider.employeesApi
    }

    @Provides
    @Singleton
    fun provideEmployeesApi2(retrofitProvider: RetrofitProvider): EmployeesApi2 {
        return retrofitProvider.employeesApi2
    }
}