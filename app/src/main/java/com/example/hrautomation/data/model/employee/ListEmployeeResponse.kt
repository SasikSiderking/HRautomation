package com.example.hrautomation.data.model.employee

import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.utils.Mapper

data class ListEmployeeResponse(
    val id: Long,
    val username: String,
    val post: String
)

class ListEmployeeResponseToListEmployeeMapper : Mapper<ListEmployeeResponse, ListEmployee> {
    override fun convert(model: ListEmployeeResponse): ListEmployee = ListEmployee(model.id, model.username, model.post)
}