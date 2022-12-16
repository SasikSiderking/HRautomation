package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hrautomation.domain.repository.TokenRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(private val context: Context) : TokenRepository {
    private val preferences: SharedPreferences by lazy { context.getSharedPreferences(PREF_ACCESS, Context.MODE_PRIVATE) }

    override fun getAccessToken(): String? {
        Timber.i("Полученный аксесТокен")
        Timber.i(preferences.getString(ACC_TOKEN, null).toString())
        return preferences.getString(ACC_TOKEN, null)
    }

    override fun saveAccessToken(token: String) {
        Timber.i("Сохраненный аксес токен")
        Timber.i(token)
        preferences.edit().putString(ACC_TOKEN, token).apply()
    }

    override fun getRefreshToken(): String? {
        Timber.i("Полученный рефрешТокен")
        Timber.i(preferences.getString(REF_TOKEN, null))
        return preferences.getString(REF_TOKEN, null)
    }

    override fun saveRefreshToken(refToken: String) {
        Timber.i("Сохраненный рефрешТокен")
        Timber.i(refToken)
        preferences.edit().putString(REF_TOKEN, refToken).apply()
    }

    override fun getUserId(): Long? {
        return preferences.getString(USER_ID, null)?.toLong()
    }

    override fun saveUserId(userId: Long) {
        preferences.edit().putString(USER_ID, userId.toString()).apply()
    }

    private companion object {
        const val PREF_ACCESS = "app"
        const val ACC_TOKEN = "accessToken"
        const val REF_TOKEN = "refreshToken"
        const val USER_ID = "userId"
    }
}