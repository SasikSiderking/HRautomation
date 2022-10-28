package com.example.hrautomation.domain.repository

interface UserRepository {
    suspend fun checkEmail(email: String): Boolean
    suspend fun confirmEmail(email: String, code: String): String
}