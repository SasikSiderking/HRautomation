package com.example.hrautomation.domain.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
)