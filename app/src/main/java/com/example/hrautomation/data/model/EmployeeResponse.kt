package com.example.hrautomation.data.model

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.Mapper

data class EmployeeResponse(
    val id: Long,
    val userName: String,
    val email: String,
    var project: String,
    val post: String,
    var info: String,
    val role: String
)

class EmployeesResponseToEmployeesMapper : Mapper<EmployeeResponse, Employee> {
    override fun convert(model: EmployeeResponse): Employee {
        return Employee(model.id, model.userName, model.email, model.project, model.post, model.info)
    }
}