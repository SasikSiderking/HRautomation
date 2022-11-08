package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.EmployeeResponse
import retrofit2.Response

class EmployeesApi2 : EmployeesApi {
    val emp1 = EmployeeResponse(0, "Alexander Sidorkin", "Sidrsidsashok@mail.ru", "HRautomation", "Android Developer", "-_-", "notadmin")
    val emp2 = EmployeeResponse(
        1,
        "Elizabet Bythelook",
        "Bayliz@mail.ru",
        "HRautomation",
        "Backend Developer",
        "amogusamogusamogusamogusamogusamogusvamogusamogusamogusamogusamogusamogus",
        "admin"
    )
    val emp3 = EmployeeResponse(2, "Ariel Kogut", "GoldFish@gmail.com", "HRautomation", "Fronendt Developer", "fish", "notadmin")
    val list: List<EmployeeResponse> = listOf(emp1, emp2, emp3)
    override suspend fun getEmployeesResponse(): Response<List<EmployeeResponse>> {
        return (Response.success(list))
    }

    override suspend fun getEmployeeResponse(id: Long): Response<EmployeeResponse> {
        return (Response.success(list.find { it.id == id }))
    }
}