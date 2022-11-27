package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.TokenResponse
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
                Date(2001, 10, 1),
                "https://studybreaks.com/wp-content/uploads/2020/09/557D69DA-1FD2-494E-826B-C7E98FEF99BA_1_201_a-e1600797919133.jpeg"
            )
        )
    }

    override suspend fun saveUser(user: EmployeeResponse) {
    }
}