package com.example.hrautomation.data.repository

import android.content.Context
import com.example.hrautomation.domain.repository.IUserRepository
import javax.inject.Singleton

@Singleton
class UserRepository constructor(private val context: Context): IUserRepository {
    override fun getToken(): IUserRepository.GetTokenEvent {
       return IUserRepository.GetTokenEvent.Token("TOKEN")
    }
}