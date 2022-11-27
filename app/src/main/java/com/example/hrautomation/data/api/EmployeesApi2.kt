package com.example.hrautomation.data.api

import com.example.hrautomation.data.model.EmployeeResponse
import retrofit2.Response
import java.util.Date

class EmployeesApi2 : EmployeesApi {
    val emp1 = EmployeeResponse(
        0,
        "Alexander Sidorkin",
        "Sidrsidsashok@mail.ru",
        "HRautomation",
        "Android Developer",
        "-_-",
        "notadmin",
        Date(1, 7, 7),
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fponylike.ru%2Fserialy%2Fkto-takoj-geralt-iz-vedmaka-i-otkuda-u-nego-magiya%2F&psig=AOvVaw2NvyDheR2rZiwpKVoz_qoi&ust=1669642936807000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCNCe1eO-zvsCFQAAAAAdAAAAABAE"

    )
    val emp2 = EmployeeResponse(
        1,
        "Elizabet Bythelook",
        "Bayliz@mail.ru",
        "HRautomation",
        "Backend Developer",
        "amogusamogusamogusamogusamogusamogusvamogusamogusamogusamogusamogusamogus",
        "admin",
        Date(1, 7, 7),
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fponylike.ru%2Fserialy%2Fkto-takoj-geralt-iz-vedmaka-i-otkuda-u-nego-magiya%2F&psig=AOvVaw2NvyDheR2rZiwpKVoz_qoi&ust=1669642936807000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCNCe1eO-zvsCFQAAAAAdAAAAABAE"
    )
    val emp3 = EmployeeResponse(
        2,
        "Ariel Kogut",
        "GoldFish@gmail.com",
        "HRautomation",
        "Frontend Developer",
        "fish",
        "notadmin",
        Date(1, 7, 7),
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fponylike.ru%2Fserialy%2Fkto-takoj-geralt-iz-vedmaka-i-otkuda-u-nego-magiya%2F&psig=AOvVaw2NvyDheR2rZiwpKVoz_qoi&ust=1669642936807000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCNCe1eO-zvsCFQAAAAAdAAAAABAE"

    )
    val list: List<EmployeeResponse> = listOf(emp1, emp2, emp3)
    override suspend fun getEmployeesResponse(): Response<List<EmployeeResponse>> {
        return (Response.success(list))
    }

    override suspend fun getEmployeeResponse(id: Long): Response<EmployeeResponse> {
        return (Response.success(list.find { it.id == id }))
    }
}