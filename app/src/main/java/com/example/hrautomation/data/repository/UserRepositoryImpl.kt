package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.UserApi
import com.example.hrautomation.data.model.employee.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.domain.model.employees.Employee
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val tokenRepo: TokenRepository
) : UserRepository {

    override suspend fun getUser(id: Long): Employee {
        return employeesResponseToEmployeesMapper.convert(userApi.getUser(id))
    }

    override suspend fun saveUser(project: String, info: String) {
        tokenRepo.getUserId()?.let { userId: Long ->

            val oldUser = userApi.getUser(userId)
            val newUser = oldUser.copy(project = project, about = info)

            return userApi.saveUser(newUser)
        } ?: throw IllegalStateException("User id is null")
    }

    override suspend fun uploadProfileImage(file: ByteArray, userId: Long) {
        userApi.uploadProfileImage(
            userId,
            MultipartBody.Part.createFormData("file", "androidProfilePic.png", file.toRequestBody("image/*".toMediaType()))
        )
    }
}