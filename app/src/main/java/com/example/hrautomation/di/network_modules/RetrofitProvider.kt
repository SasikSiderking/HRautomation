package com.example.hrautomation.di.network_modules

import com.example.hrautomation.BuildConfig
import com.example.hrautomation.data.api.AuthInterceptor
import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.api.FaqApi
import com.example.hrautomation.data.api.FaqApi2
import com.example.hrautomation.data.api.ProductApi
import com.example.hrautomation.data.api.UserApi
import com.example.hrautomation.data.api.UserApi2
import com.example.hrautomation.data.repository.TokenRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitProvider @Inject constructor(private val tokenRepositoryImpl: TokenRepositoryImpl) {


    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(tokenRepositoryImpl.getAccessToken() ?: ""))
            .addInterceptor(logging)
            .build()
    }


    private val logging: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create()
    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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

    val faqApi: FaqApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }

    val faqApi2: FaqApi2 by lazy { FaqApi2() }

    val productApi: ProductApi by lazy {
        retrofitBuilder
            .client(httpClient)
            .build()
            .create()
    }
}