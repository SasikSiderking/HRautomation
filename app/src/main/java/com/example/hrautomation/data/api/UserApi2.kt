package com.example.hrautomation.data.api

import retrofit2.Response

class UserApi2 : UserApi {
    override suspend fun checkEmail(email: String): Response<Boolean> {
        return Response.success(true)
    }

    override fun confirmEmail(email: String, code: String): Response<String> {
        return Response.success("123")
    }
}