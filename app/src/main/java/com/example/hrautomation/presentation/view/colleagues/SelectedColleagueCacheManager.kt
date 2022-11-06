package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.presentation.model.ColleagueItem
import javax.inject.Inject

class SelectedColleagueCacheManager @Inject constructor() {

    private var cachedSelectedEmployee: ColleagueItem? = null

    fun setSelectedEmployee(employee: ColleagueItem) {
        cachedSelectedEmployee = employee
    }

    fun getSelectedEmployee(): ColleagueItem? {
        return cachedSelectedEmployee
    }

}