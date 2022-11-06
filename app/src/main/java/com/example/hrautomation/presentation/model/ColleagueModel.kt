package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ColleagueItem(
    val name: String,
    val email: String,
    val project: String,
    val post: String,
    val info: String,
    override val id: String = name
) : BaseListItem

class EmployeeToColleagueItemMapper : Mapper<Employee, ColleagueItem> {
    override fun convert(model: Employee): ColleagueItem =
        ColleagueItem(model.name, model.email, model.project, model.post, model.info)
}