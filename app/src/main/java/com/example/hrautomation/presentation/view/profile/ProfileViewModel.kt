package com.example.hrautomation.presentation.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.R
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.colleagues.EmployeeItem
import com.example.hrautomation.presentation.model.colleagues.EmployeeToEmployeeItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val tokenRepo: TokenRepository,
    private val dispatchers: CoroutineDispatchers,
    private val employeeToEmployeeItemMapper: EmployeeToEmployeeItemMapper
) : BaseViewModel() {
    val data: LiveData<EmployeeItem>
        get() = _data
    private val _data = MutableLiveData<EmployeeItem>()

    val message: LiveData<Int?>
        get() = _message
    private val _message = MutableLiveData<Int?>()

    fun clearMessageState() {
        _message.postValue(null)
    }

    fun reload() {
        clearExceptionState()
        jobs.clear()
        loadData()
    }

    init {
        loadData()
    }

    private fun loadData() {
        jobs.add(
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.io,
                doOnLaunch = {
                    tokenRepo.getUserId()?.let { userId ->
                        val user = userRepo.getUser(userId)
                        _data.postValue(employeeToEmployeeItemMapper.convert(user))
                    } ?: throw IllegalStateException("No auth token")
                },
                doOnError = { error ->
                    Timber.e(error)
                    _exception.postValue(error)
                }
            )
        )
    }

    fun saveData(project: String, info: String) {
        jobs.add(
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.io,
                doOnLaunch = {
                    withContext(NonCancellable) {
                        userRepo.saveUser(project, info)
                        _message.postValue(R.string.profile_save_success)
                    }
                },
                doOnError = { error ->
                    Timber.e(error)
                    _exception.postValue(error)
                }
            )
        )
    }
}