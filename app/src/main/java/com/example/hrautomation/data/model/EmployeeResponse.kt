package com.example.hrautomation.data.model

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.Mapper

data class EmployeeResponse(
    val id: Long,
    val username: String,
    val email: String,
    var project: String,
    val post: String,
    var about: String,
    val admin: String
)

class EmployeesResponseToEmployeesMapper : Mapper<EmployeeResponse, Employee> {
    override fun convert(model: EmployeeResponse): Employee {
        return Employee(model.id, model.username, model.email, model.project, model.post, model.about)
    }
}