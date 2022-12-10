package com.example.hrautomation.presentation.view.colleagues.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.colleagues.EmployeeItem
import com.example.hrautomation.presentation.model.colleagues.EmployeeToEmployeeItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(
    private val repo: EmployeesRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeeToEmployeeItemMapper: EmployeeToEmployeeItemMapper
) : BaseViewModel() {
    val selectedEmployee: LiveData<EmployeeItem>
        get() = _selectedEmployee
    private val _selectedEmployee = MutableLiveData<EmployeeItem>()

    fun reload(id: Long) {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData(id)
    }

    fun loadData(id: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                _selectedEmployee.postValue(employeeToEmployeeItemMapper.convert(repo.getEmployee(id)))
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }
}