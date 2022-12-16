package com.example.hrautomation.data.model.employee

import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.utils.Mapper

data class GetEmployees(
    val users: List<ListEmployeeResponse>,
    val pages: Long
)

data class ListEmployeeResponse(
    val id: Long,
    val username: String,
    val post: String,
    val pictureUrl: String?
)

class ListEmployeeResponseToListEmployeeMapper : Mapper<ListEmployeeResponse, ListEmployee> {
    override fun convert(model: ListEmployeeResponse): ListEmployee =
        ListEmployee(model.id, model.username, model.post, model.pictureUrl ?: "https://cdn.mos.cms.futurecdn.net/PzPq6Pbn5RqgrWunhEx6rg.jpg")
}