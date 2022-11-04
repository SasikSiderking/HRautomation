package com.example.hrautomation.presentation.view.employee

import androidx.lifecycle.ViewModel
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.EmployeesRepository
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(private val repo: EmployeesRepository) : ViewModel() {
    fun getSelectedEmployee(): Employee {
        return repo.getSelectedEmployee()
    }
}