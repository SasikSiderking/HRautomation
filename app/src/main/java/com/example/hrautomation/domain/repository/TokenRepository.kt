package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Token

interface TokenRepository {
    fun getAccessToken(): String?

    //    fun setAccessToken(token: String?)
    fun getRefreshToken(): String?

    //    fun setRefreshToken(refToken: String?)
    fun getUserId(): Long?

    //    fun setUserId(userId: Long)
    suspend fun checkEmail(email: String)
    suspend fun confirmEmail(email: String, code: String): Token

    suspend fun logout()

    suspend fun login(token: Token)

    suspend fun refreshToken(): Token?
}