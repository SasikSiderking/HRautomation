package com.example.hrautomation.data.model.tokens

import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.utils.Mapper

data class TokenResponse(
    val type: String,
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val userName: String
)

class TokenResponseToTokenMapper : Mapper<TokenResponse, Token> {
    override fun convert(model: TokenResponse): Token = Token(model.accessToken, model.refreshToken, model.userId)
}