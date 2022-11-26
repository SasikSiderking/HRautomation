package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.UserApi2
import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.TokenResponse
import com.example.hrautomation.data.model.TokenResponseToTokenMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.utils.asResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi2,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val tokenResponseToTokenMapper: TokenResponseToTokenMapper
) :
    UserRepository {

    override suspend fun checkEmail(email: String): Result<Boolean> {
        return userApi.checkEmail(email).asResult { isChecked: Boolean ->
            isChecked
        }
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
}