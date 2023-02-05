package com.example.hrautomation.di.network_modules

import com.example.hrautomation.BuildConfig
import com.example.hrautomation.data.api.TokenApi
import com.example.hrautomation.utils.retrofit_adapter.CustomAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TokenApiProvider @Inject constructor() {

    private val logging: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //    private val contentType = "application/json".toMediaType()
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create()

    //    @OptIn(ExperimentalSerializationApi::class)
    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CustomAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    private val unauthorizedHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(logging)
                }
            }
            .build()
    }

    val tokenApi: TokenApi by lazy {
        retrofitBuilder
            .client(unauthorizedHttpClient)
            .build()
            .create()
    }
}