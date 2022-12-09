package com.example.hrautomation.data.model.employee

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.Mapper
import java.util.Date

data class EmployeeResponse(
    val id: Long,
    val username: String,
    val email: String,
    val project: String,
    val post: String,
    val about: String,
    val admin: String,
    val birthDate: Date,
    val img: String?
)

class EmployeesResponseToEmployeesMapper : Mapper<EmployeeResponse, Employee> {
    override fun convert(model: EmployeeResponse): Employee {
        return Employee(
            model.id,
            model.username,
            model.email,
            model.project,
            model.post,
            model.about,
            "https://cdn.mos.cms.futurecdn.net/PzPq6Pbn5RqgrWunhEx6rg.jpg"
        )
    }
}