package com.example.hrautomation.domain.repository

interface ITokenRepository {
    fun getToken(): String?
    fun saveToken(token: String)
}