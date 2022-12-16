package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.TokenResponse
import com.example.hrautomation.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenApi: TokenApi,
    private val tokenRepo: TokenRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            with(getUpdatedToken()) {
                if (this != null) {
                    tokenRepo.saveAccessToken(accessToken)
                    tokenRepo.saveRefreshToken(refreshToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .build()
                } else {
                    null
                }
            }
        }
    }

    private suspend fun getUpdatedToken(): TokenResponse? {
        return tokenRepo.getRefreshToken()?.let {
            try {
                tokenApi.refreshToken(it)
            } catch (exception: Exception) {
                Timber.e(exception)
                null
            }
        }
    }
}