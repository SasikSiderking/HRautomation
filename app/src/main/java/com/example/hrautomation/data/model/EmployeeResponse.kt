package com.example.hrautomation.data.model

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.IMapper

data class EmployeeResponse(
    val id: Int,
    val name: String,
    val email: String,
    val project: String,
    val post: String,
    val info: String,
    val role: String
)

class EmployeesResponseToEmployeesMapper : IMapper<EmployeeResponse, Employee> {
    override fun convert(model: EmployeeResponse): Employee {
        return Employee(model.name, model.email, model.project, model.post, model.info)
    }
}