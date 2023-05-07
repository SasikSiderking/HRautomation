package com.example.hrautomation.data.api

import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenRepo: TokenRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            with(getUpdatedToken()) {
                if (this != null) {
                    response.request.newBuilder()
                        .header("Authorization", accessToken)
                        .build()
                } else {
                    null
                }
            }
        }
    }

    private suspend fun getUpdatedToken(): Token? {
        return tokenRepo.refreshToken()
    }
}