package com.example.hrautomation.di.network_modules

import com.example.hrautomation.data.api.*
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

    @Provides
    @Singleton
    fun provideRestaurantApi(retrofitProvider: RetrofitProvider): RestaurantsApi {
        return RestaurantsApi2()
    }
}