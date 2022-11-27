package com.example.hrautomation.presentation.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.R
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.presentation.model.EmployeeItem
import com.example.hrautomation.presentation.model.EmployeeToEmployeeItemMapper
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val tokenRepo: TokenRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeeToEmployeeItemMapper: EmployeeToEmployeeItemMapper
) : ViewModel() {
    val data: LiveData<EmployeeItem>
        get() = _data
    private val _data = MutableLiveData<EmployeeItem>()

    val exception: LiveData<Throwable?>
        get() = _exception
    private val _exception = MutableLiveData<Throwable?>()

    val message: LiveData<Int?>
        get() = _message
    private val _message = MutableLiveData<Int?>()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    fun clearMessageState() {
        _message.postValue(null)
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            tokenRepo.getUserId()?.let { userId: Long ->
                userRepo.getUser(userId)
                    .onSuccess { user ->
                        _data.postValue(employeeToEmployeeItemMapper.convert(user))
                    }
                    .onFailure { exception: Throwable ->
                        Timber.e(exception)
                        _exception.postValue(exception)
                    }
            } ?: run {
                throw IllegalStateException("No auth token")
            }
        }
    }

    fun saveData(project: String, info: String) {
        viewModelScope.launch(dispatchers.io) {
            userRepo.saveUser(project, info)
                .onSuccess {
                    _message.postValue(R.string.profile_save_success)
                }
                .onFailure {
                    _message.postValue(R.string.toast_overall_error)
                }
        }
    }
}