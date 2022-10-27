package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hrautomation.domain.repository.ITokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(private val context: Context) : ITokenRepository {
    override fun getToken(): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences("app", Context.MODE_PRIVATE)
        return preferences.getString("token", null)
    }


    override fun saveToken(token: String) {
        val preferences: SharedPreferences =
            context.getSharedPreferences("app", Context.MODE_PRIVATE)
        preferences.edit().putString("token", token).apply()
    }
}