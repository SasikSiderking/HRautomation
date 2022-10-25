package com.example.hrautomation.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor constructor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("appid", "hrautomation")
                .addHeader("deviceplatform", "android")
                .addHeader("Authorization", "Bearer$token")
                .build()
        )
    }
}