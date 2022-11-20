package com.example.hrautomation.presentation.view.colleagues.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.model.EmployeeItem
import com.example.hrautomation.presentation.model.EmployeeToEmployeeItemMapper
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(
    private val repo: EmployeesRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeeToEmployeeItemMapper: EmployeeToEmployeeItemMapper
) : ViewModel() {
    val selectedEmployee: LiveData<EmployeeItem>
        get() = _selectedEmployee
    private val _selectedEmployee = MutableLiveData<EmployeeItem>()

    val exception: LiveData<Throwable?>
        get() = _exception
    private var _exception = MutableLiveData<Throwable?>()

    fun clearToastState() {
        _exception = MutableLiveData<Throwable?>()
    }

    fun loadData(id: Long) {
        viewModelScope.launch(dispatchers.io) {
            val result = repo.getEmployee(id)
            result.onSuccess { employee: Employee ->
                _selectedEmployee.postValue(employeeToEmployeeItemMapper.convert(employee))
            }
            result.onFailure { exception: Throwable ->
                Timber.e(exception)
                _exception.postValue(exception)
            }
        }
    }
}