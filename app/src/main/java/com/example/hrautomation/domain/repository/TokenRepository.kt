package com.example.hrautomation.domain.repository

interface TokenRepository {
    fun getToken(): String?
    fun saveToken(token: String)
}