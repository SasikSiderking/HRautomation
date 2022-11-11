package com.example.hrautomation.data.model

data class TokenResponse(
    val type: String,
    val accessToken: String,
    val refreshToken: String
)