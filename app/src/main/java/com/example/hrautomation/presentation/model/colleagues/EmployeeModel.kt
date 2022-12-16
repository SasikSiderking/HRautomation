package com.example.hrautomation.presentation.model.colleagues

import com.example.hrautomation.domain.model.employees.Employee
import com.example.hrautomation.utils.Mapper

data class EmployeeItem(
    val name: String,
    val email: String,
    val project: String,
    val post: String,
    val info: String,
    val pictureUrl: String?
)

class EmployeeToEmployeeItemMapper : Mapper<Employee, EmployeeItem> {
    override fun convert(model: Employee): EmployeeItem =
        EmployeeItem(model.name, model.email, model.project, model.post, model.info, model.pictureUrl)
}