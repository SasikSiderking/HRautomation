package com.example.hrautomation.data.model

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.Mapper

data class EmployeeResponse(
    val id: Long,
    val name: String,
    val email: String,
    val project: String,
    val post: String,
    val info: String,
    val role: String
)

class EmployeesResponseToEmployeesMapper : Mapper<EmployeeResponse, Employee> {
    override fun convert(model: EmployeeResponse): Employee {
        return Employee(model.id, model.name, model.email, model.project, model.post, model.info)
    }
}