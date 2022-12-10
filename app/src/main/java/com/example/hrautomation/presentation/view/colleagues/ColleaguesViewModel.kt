package com.example.hrautomation.presentation.view.colleagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.colleagues.EmployeeToColleagueItemMapper
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class ColleaguesViewModel @Inject constructor(
    private val repo: EmployeesRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeesToColleagueItemMapper: EmployeeToColleagueItemMapper
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

    private var reservedData: List<ListEmployee> = emptyList()

    init {
        loadData()
    }

    fun reload() {
        jobs.clear()
        loadData()
    }

    private fun loadData() {
        jobs.add(
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.io,
                doOnLaunch = {
                    reservedData = repo.getEmployeeList(PAGE_NUMBER, PAGE_SIZE, ColleaguesSortBy.NAME)
                    _data.postValue(reservedData.map { employeesToColleagueItemMapper.convert(it) })
                },
                doOnError = { error ->
                    Timber.e(error)
                    _exception.postValue(error)
                }
            )
        )
    }

    fun performSearch(name: String) {
        jobs.add(
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.default,
                doOnLaunch = {
                    if (name.isNotEmpty()) {
                        _data.postValue(
                            reservedData.filter { employee ->
                                employee.username.contains(name, ignoreCase = true)
                            }.map { employeesToColleagueItemMapper.convert(it) }
                        )
                    } else {
                        if (reservedData.isNotEmpty()) {
                            _data.postValue(reservedData.map { employeesToColleagueItemMapper.convert(it) })
                        }
                    }
                },
                doOnError = { error ->
                    Timber.e(error)
                    _exception.postValue(error)
                }
            )
        )
    }

    private companion object {
        const val PAGE_SIZE = 100
        const val PAGE_NUMBER = 0
    }
}