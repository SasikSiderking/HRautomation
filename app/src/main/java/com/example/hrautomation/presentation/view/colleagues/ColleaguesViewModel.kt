package com.example.hrautomation.presentation.view.colleagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.ColleagueItem
import com.example.hrautomation.presentation.model.EmployeeToColleagueItemMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class ColleaguesViewModel @Inject constructor(
    private val repo: EmployeesRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeesToColleagueItemMapper: EmployeeToColleagueItemMapper,
    private val selectedColleagueCacheManager: SelectedColleagueCacheManager
) : ViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>(emptyList())

    private var reservedData: List<Employee> = emptyList()

    fun selectEmployee(employee: ColleagueItem) {
        selectedColleagueCacheManager.setSelectedEmployee(employee)
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            reservedData = repo.getEmployeeList()
            _data.postValue(reservedData.map { employeesToColleagueItemMapper.convert(it) })
        }
    }

    fun performSearch(name: String) {
        viewModelScope.launch(dispatchers.default) {
            if (name.isNotEmpty()) {
                _data.postValue(
                    reservedData.filter { employee ->
                        employee.name.contains(name, ignoreCase = true)
                    }.map { employeesToColleagueItemMapper.convert(it) }
                )
            } else {
                _data.postValue(reservedData.map { employeesToColleagueItemMapper.convert(it) })
            }
        }
    }
}