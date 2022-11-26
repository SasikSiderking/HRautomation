package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.UserApi
import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.TokenResponse
import com.example.hrautomation.data.model.TokenResponseToTokenMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.utils.asResult
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val tokenResponseToTokenMapper: TokenResponseToTokenMapper
) :
    UserRepository {

    private var user: EmployeeResponse? = null

    override suspend fun checkEmail(email: String) {
        userApi.checkEmail(email)
    }

    override suspend fun confirmEmail(email: String, code: String): Result<Token> {
        return userApi.confirmEmail(email, code).asResult { tokenResponse: TokenResponse ->
            tokenResponseToTokenMapper.convert(tokenResponse)
        }
    }

    override suspend fun getUser(id: Long): Result<Employee> {
        with(userApi.getUser(id)) {
            Timber.i(this.body().toString())
            asResult { employeeResponse ->
                employeeResponse
            }.onSuccess { employeeResponse ->
                user = employeeResponse
            }
            return asResult { employeeResponse: EmployeeResponse ->
                employeesResponseToEmployeesMapper.convert(employeeResponse)
            }
        }
    }

    override suspend fun saveUser(project: String, info: String) {
        user?.let {
            it.project = project
            it.info = info
            userApi.saveUser(it)
        }
    }
}