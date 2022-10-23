package com.example.hrautomation.data.api

import com.example.hrautomation.data.repository.UserRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val userRepository: UserRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("appid","hrautomation")
                .addHeader("deviceplatform", "android")
                .addHeader("Authorization", "Bearer" + userRepository.getToken())
                .build()
        )
    }
}