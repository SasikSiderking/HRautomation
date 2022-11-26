package com.example.hrautomation.domain.repository

interface TokenRepository {
    fun getAccessToken(): String?
    fun saveAccessToken(token: String)
    fun getRefreshToken(): String?
    fun saveRefreshToken(refToken: String)
    fun getUserId(): Long?
    fun saveUserId(userId: Long)
}