package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.TokenResponse
import retrofit2.Response

class UserApi2 : UserApi {
    override suspend fun checkEmail(email: String): Response<Boolean> {
        return Response.success(true)
    }

    override suspend fun confirmEmail(email: String, code: String): Response<TokenResponse> {
        return Response.success(TokenResponse("Bearer", "access", "refresh"))
    }
}