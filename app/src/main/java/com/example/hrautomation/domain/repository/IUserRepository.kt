package com.example.hrautomation.domain.repository

interface IUserRepository {
    fun getToken(): GetTokenEvent

    sealed class GetTokenEvent{
        object NoToken : GetTokenEvent()
        data class Token(val token: String): GetTokenEvent()
    }
}