package com.example.hrautomation.domain.repository

interface IUserRepository {
    suspend fun checkEmail(email: String): Boolean
    suspend fun confirmEmail(email: String, code: String): String
    fun getToken(): String?
    fun saveToken(token: String)
}