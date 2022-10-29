package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.domain.model.Employee
import javax.inject.Inject

class SelectedColleagueCacheManager @Inject constructor() {

    private var cachedSelectedEmployee: Employee? = null

    fun setSelectedEmployee(employee: Employee) {
        cachedSelectedEmployee = employee
    }

    fun getSelectedEmployee(): Employee? {
        return cachedSelectedEmployee
    }

}