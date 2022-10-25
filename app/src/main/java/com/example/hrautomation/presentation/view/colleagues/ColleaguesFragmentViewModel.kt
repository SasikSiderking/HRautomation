package com.example.hrautomation.presentation.view.colleagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.IEmployeesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ColleaguesFragmentViewModel @Inject constructor(private val repo: IEmployeesRepository) : ViewModel() {

    val data: LiveData<List<Employee>>
        get() = _data
    private val _data = MutableLiveData<List<Employee>>(emptyList())

    private val _selectedEmployee = MutableLiveData<Employee>()
    val selectedEmployee: LiveData<Employee> get() = _selectedEmployee

    fun selectEmployee(employee: Employee) {
        _selectedEmployee.value = employee
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val employeeList = repo.getEmployeeList()
            _data.postValue(employeeList)
        }
    }
}