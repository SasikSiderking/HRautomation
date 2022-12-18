package com.example.hrautomation.di.network_modules

import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.api.FaqApi
import com.example.hrautomation.data.api.ProductApi
import com.example.hrautomation.data.api.TokenApi
import com.example.hrautomation.data.api.UserApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideTokenApi(tokenApiProvider: TokenApiProvider): TokenApi {
        return tokenApiProvider.tokenApi
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofitProvider: RetrofitProvider): UserApi {
        return retrofitProvider.userApi
    }

    @Provides
    @Singleton
    fun provideEmployeesApi(retrofitProvider: RetrofitProvider): EmployeesApi {
        return retrofitProvider.employeesApi
    }

    @Provides
    @Singleton
    fun provideFaqApi(retrofitProvider: RetrofitProvider): FaqApi {
        return retrofitProvider.faqApi
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofitProvider: RetrofitProvider): ProductApi {
        return retrofitProvider.productApi
    }
}