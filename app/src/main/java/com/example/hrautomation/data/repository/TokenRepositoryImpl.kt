package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hrautomation.data.api.TokenApi
import com.example.hrautomation.data.model.TokenResponseToTokenMapper
import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.utils.runSuspendCatching
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val context: Context,
    private val tokenApi: TokenApi,
    private val tokenResponseToTokenMapper: TokenResponseToTokenMapper
) : TokenRepository {

    private val preferences: SharedPreferences by lazy { context.getSharedPreferences(PREF_ACCESS, Context.MODE_PRIVATE) }

    override suspend fun checkEmail(email: String) {
        return tokenApi.checkEmail(email)
    }

    override suspend fun confirmEmail(email: String, code: String): Token {
        return tokenResponseToTokenMapper.convert(tokenApi.confirmEmail(email, code))
    }

    override fun logout() {
        setAccessToken(null)
        setRefreshToken(null)
    }

    override fun login(token: Token) {
        setAccessToken(token.accessToken)
        setRefreshToken(token.refreshToken)
        setUserId(token.userId)
    }

    override suspend fun refreshToken(): Token? {
        return runSuspendCatching(
            onSuccessBlock = {
                return getRefreshToken()?.let {
                    val newToken =
                        tokenResponseToTokenMapper.convert(tokenApi.refreshToken(it.toRequestBody("application/json".toMediaType())))
                    login(newToken)
                    return newToken
                }
            },
            onErrorBlock = { exception ->
                Timber.e(exception)
                null
            }
        )
    }

    override fun getAccessToken(): String? {
        return preferences.getString(ACC_TOKEN, null)
    }

    private fun setAccessToken(token: String?) {
        preferences.edit().putString(ACC_TOKEN, token).apply()
    }

    override fun getRefreshToken(): String? {
        return preferences.getString(REF_TOKEN, null)
    }

    private fun setRefreshToken(refToken: String?) {
        preferences.edit().putString(REF_TOKEN, refToken).apply()
    }

    override fun getUserId(): Long? {
        return preferences.getString(USER_ID, null)?.toLong()
    }

    private fun setUserId(userId: Long) {
        preferences.edit().putString(USER_ID, userId.toString()).apply()
    }

    private companion object {
        const val PREF_ACCESS = "app"
        const val ACC_TOKEN = "accessToken"
        const val REF_TOKEN = "refreshToken"
        const val USER_ID = "userId"
    }
}