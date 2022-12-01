package com.example.hrautomation.presentation.view.colleagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.colleagues.EmployeeToColleagueItemMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class ColleaguesViewModel @Inject constructor(
    private val repo: EmployeesRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeesToColleagueItemMapper: EmployeeToColleagueItemMapper
) : ViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

    private var reservedData: List<ListEmployee> = emptyList()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            reservedData = repo.getEmployeeList(PAGE_NUMBER, PAGE_SIZE, ColleaguesSortBy.NAME)
            _data.postValue(reservedData.map { employeesToColleagueItemMapper.convert(it) })
        }
    }

    fun performSearch(name: String) {
        viewModelScope.launch(dispatchers.default) {
            if (name.isNotEmpty()) {
                _data.postValue(
                    reservedData.filter { employee ->
                        employee.username.contains(name, ignoreCase = true)
                    }.map { employeesToColleagueItemMapper.convert(it) }
                )
            } else {
                _data.postValue(reservedData.map { employeesToColleagueItemMapper.convert(it) })
            }
        }
    }

    private companion object {
        const val PAGE_SIZE = 20
        const val PAGE_NUMBER = 1
    }
}