package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hrautomation.domain.repository.IUserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val context: Context): IUserRepository {
    override fun getToken(): String? {
        val preferences: SharedPreferences = context.getSharedPreferences("app",Context.MODE_PRIVATE)
        preferences.getString("token",null)
       return  preferences.getString("token",null)
    }
}