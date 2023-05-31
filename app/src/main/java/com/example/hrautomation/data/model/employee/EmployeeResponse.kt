package com.example.hrautomation.data.model.employee

import com.example.hrautomation.domain.model.employees.Employee
import com.example.hrautomation.utils.Mapper

data class EmployeeResponse(
    val id: Long,
    val username: String,
    val email: String,
    val project: String?,
    val post: String?,
    val about: String?,
    val pictureUrl: String?
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
            model.pictureUrl
        )
    }
}