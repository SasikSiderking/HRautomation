package com.example.hrautomation.presentation.model.colleagues

import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.Mapper

data class ListedColleagueItem(
    override val id: Long,
    val name: String,
    val post: String,
    val pictureUrl: String?
) : BaseListItem

class EmployeeToColleagueItemMapper : Mapper<ListEmployee, ListedColleagueItem> {
    override fun convert(model: ListEmployee): ListedColleagueItem =
        ListedColleagueItem(model.id, model.username, model.post ?: "", model.pictureUrl)
}