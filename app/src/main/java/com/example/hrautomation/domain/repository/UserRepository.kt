package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.employees.Employee

interface UserRepository {
    suspend fun getUser(id: Long): Employee
    suspend fun saveUser(project: String, info: String)
    suspend fun uploadProfileImage(file: ByteArray, userId: Long)
}