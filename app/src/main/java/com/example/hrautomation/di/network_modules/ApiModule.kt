package com.example.hrautomation.di.network_modules

import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.api.FaqApi
import com.example.hrautomation.data.api.FaqApi2
import com.example.hrautomation.data.api.ProductApi
import com.example.hrautomation.data.api.ProductApi2
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
    fun provideFaqApi(retrofitProvider: RetrofitProvider): FaqApi {
        return retrofitProvider.faqApi
    }

    @Provides
    @Singleton
    fun provideFaqApi2(retrofitProvider: RetrofitProvider): FaqApi2 {
        return retrofitProvider.faqApi2
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofitProvider: RetrofitProvider): ProductApi {
        return ProductApi2()
    }
}