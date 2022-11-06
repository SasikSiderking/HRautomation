package com.example.hrautomation.presentation.view.colleagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.EmployeesRepository
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

    val data: LiveData<List<ColleagueItem>>
        get() = _data
    private val _data = MutableLiveData<List<ColleagueItem>>(emptyList())

    private var reservedData: List<ColleagueItem> = emptyList()

    fun selectEmployee(employee: ColleagueItem) {
        selectedColleagueCacheManager.setSelectedEmployee(employee)
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            val employeeList = repo.getEmployeeList()
            reservedData = employeeList.map { employeesToColleagueItemMapper.convert(it) }
            _data.postValue(reservedData)
        }
    }

    fun performSearch(name: String) {
        _data.value = reservedData
        viewModelScope.launch(dispatchers.default) {
            if (name.isNotEmpty()) {
                _data.postValue(
                    _data.value?.filter { employee ->
                        employee.name.contains(name, ignoreCase = true)
                    } ?: emptyList()
                )
            } else {
                _data.postValue(reservedData)
            }
        }
    }
}