package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Token

interface TokenRepository {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun getUserId(): Long?
    suspend fun checkEmail(email: String)
    suspend fun confirmEmail(email: String, code: String): Token

    fun logout()

    fun login(token: Token)

    suspend fun refreshToken(): Token?

    suspend fun sendNotificationToken()

    fun saveNotificationToken(notificationToken: String)
}