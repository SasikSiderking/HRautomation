package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.TokenResponse
import com.example.hrautomation.data.model.employee.EmployeeResponse
import retrofit2.Response
import java.util.Date

class UserApi2 : UserApi {

    override suspend fun checkEmail(email: String): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun confirmEmail(email: String, code: String): Response<TokenResponse> {
        return Response.success(TokenResponse("Bearer", "access", "refresh", 0, "Pepe"))
    }

    override suspend fun getUser(id: Long): Response<EmployeeResponse> {
        return Response.success(
            EmployeeResponse(
                0,
                "Pepe",
                "aas297@tpu.ru",
                "Олег",
                "Oleg-developer",
                "It's wensday, my dudes",
                "Not admin",
                Date(2001, 10, 1)
            )
        )
    }

    override suspend fun saveUser(user: EmployeeResponse): Response<Unit> {
        return Response.success(Unit)
    }
}