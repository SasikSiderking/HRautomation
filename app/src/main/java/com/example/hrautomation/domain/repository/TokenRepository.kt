package com.example.hrautomation.domain.repository

interface TokenRepository {
    fun getAccessToken(): String?
    fun setAccessToken(token: String?)
    fun getRefreshToken(): String?
    fun setRefreshToken(refToken: String?)
    fun getUserId(): Long?
    fun setUserId(userId: Long)
}