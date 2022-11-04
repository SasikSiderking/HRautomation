package com.example.hrautomation.presentation.view.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.EmployeesRepository
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(private val repo: EmployeesRepository) : ViewModel() {
    val selectedEmployee: LiveData<Employee>
        get() = _selectedEmployee
    private val _selectedEmployee = MutableLiveData<Employee>()

    init {
        loadData()
    }

    private fun loadData() {
        val employee = repo.getSelectedEmployee()
        _selectedEmployee.postValue(employee)
    }
}