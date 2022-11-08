package com.example.hrautomation.presentation.model

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ColleagueItem(
    override val id: Long,
    val name: String,
    val project: String,
    val post: String,
) : BaseListItem

class EmployeeToColleagueItemMapper : Mapper<Employee, ColleagueItem> {
    override fun convert(model: Employee): ColleagueItem =
        ColleagueItem(model.id, model.name, model.project, model.post)
}