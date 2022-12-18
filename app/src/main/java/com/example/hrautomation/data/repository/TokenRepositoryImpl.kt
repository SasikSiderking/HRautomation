package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hrautomation.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(private val context: Context) : TokenRepository {
    private val preferences: SharedPreferences by lazy { context.getSharedPreferences(PREF_ACCESS, Context.MODE_PRIVATE) }

    override fun getAccessToken(): String? {
        return preferences.getString(ACC_TOKEN, null)
    }

    override fun setAccessToken(token: String?) {
        preferences.edit().putString(ACC_TOKEN, token).apply()
    }

    override fun getRefreshToken(): String? {
        return preferences.getString(REF_TOKEN, null)
    }

    override fun setRefreshToken(refToken: String?) {
        preferences.edit().putString(REF_TOKEN, refToken).apply()
    }

    override fun getUserId(): Long? {
        return preferences.getString(USER_ID, null)?.toLong()
    }

    override fun setUserId(userId: Long) {
        preferences.edit().putString(USER_ID, userId.toString()).apply()
    }

    private companion object {
        const val PREF_ACCESS = "app"
        const val ACC_TOKEN = "accessToken"
        const val REF_TOKEN = "refreshToken"
        const val USER_ID = "userId"
    }
}