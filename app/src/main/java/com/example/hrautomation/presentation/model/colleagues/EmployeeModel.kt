package com.example.hrautomation.presentation.model.colleagues

import android.graphics.Bitmap
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.Mapper

data class EmployeeItem(
    val name: String,
    val email: String,
    val project: String,
    val post: String,
    val info: String,
    val img: Bitmap?
)

class EmployeeToEmployeeItemMapper : Mapper<Employee, EmployeeItem> {
    override fun convert(model: Employee): EmployeeItem =
        EmployeeItem(model.name, model.email, model.project, model.post, model.info, null)
}