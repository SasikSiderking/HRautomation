package com.example.hrautomation.presentation.view.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrautomation.presentation.model.ColleagueItem
import com.example.hrautomation.presentation.view.colleagues.SelectedColleagueCacheManager
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(private val selectedColleagueCacheManager: SelectedColleagueCacheManager) : ViewModel() {
    val selectedEmployee: LiveData<ColleagueItem>
        get() = _selectedEmployee
    private val _selectedEmployee = MutableLiveData<ColleagueItem>()

    init {
        loadData()
    }

    private fun loadData() {
        val employee = selectedColleagueCacheManager.getSelectedEmployee()
        _selectedEmployee.postValue(employee!!)
    }
}