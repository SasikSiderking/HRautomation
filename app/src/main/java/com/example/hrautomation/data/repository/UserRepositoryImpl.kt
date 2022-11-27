package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.UserApi
import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.TokenResponse
import com.example.hrautomation.data.model.TokenResponseToTokenMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.utils.asResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val tokenResponseToTokenMapper: TokenResponseToTokenMapper,
    private val tokenRepo: TokenRepository
) : UserRepository {

    override suspend fun checkEmail(email: String): Result<Unit> {
        return userApi.checkEmail(email).asResult { }
    }

    override suspend fun confirmEmail(email: String, code: String): Result<Token> {
        return userApi.confirmEmail(email, code).asResult { tokenResponse: TokenResponse ->
            tokenResponseToTokenMapper.convert(tokenResponse)
        }
    }

    override suspend fun getUser(id: Long): Result<Employee> {
        return userApi.getUser(id).asResult { employeeResponse: EmployeeResponse ->
            employeesResponseToEmployeesMapper.convert(employeeResponse)
        }
    }

    override suspend fun saveUser(project: String, info: String): Result<Unit> {
        return tokenRepo.getUserId()?.let { userId: Long ->
            val userResult = userApi.getUser(userId).asResult { it }
            if (userResult.isSuccess) {
                val oldUser = userResult.getOrThrow()
                val newUser = oldUser.copy(project = project, about = info)
                userApi.saveUser(newUser).asResult { }
            } else {
                Result.failure(userResult.exceptionOrNull()!!)
            }
        } ?: Result.failure(IllegalStateException("No auth token"))
    }
}