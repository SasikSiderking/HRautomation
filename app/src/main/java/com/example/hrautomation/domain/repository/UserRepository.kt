package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.Token

interface UserRepository {
    suspend fun checkEmail(email: String): Result<Boolean>
    suspend fun confirmEmail(email: String, code: String): Result<Token>
    suspend fun getUser(id: Long): Result<Employee>
}