package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.TokenApi
import com.example.hrautomation.data.api.UserApi
import com.example.hrautomation.data.model.TokenResponseToTokenMapper
import com.example.hrautomation.data.model.employee.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val tokenApi: TokenApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val tokenResponseToTokenMapper: TokenResponseToTokenMapper,
    private val tokenRepo: TokenRepository
) : UserRepository {

    override suspend fun checkEmail(email: String) {
        return tokenApi.checkEmail(email)
    }

    override suspend fun confirmEmail(email: String, code: String): Token {
        return tokenResponseToTokenMapper.convert(tokenApi.confirmEmail(email, code))
    }

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
}