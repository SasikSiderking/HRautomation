package com.example.hrautomation.presentation.view.loading.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmailLoginViewModel @Inject constructor(private val userRepo: UserRepository, private val dispatchers: CoroutineDispatchers) : ViewModel() {

    val isEmailCheckSuccess: LiveData<Boolean>
        get() = _isEmailCheckSuccess
    private val _isEmailCheckSuccess = MutableLiveData<Boolean>()

    val exception: LiveData<Throwable?>
        get() = _exception
    private var _exception = MutableLiveData<Throwable?>()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    fun checkEmail(email: String) {
        viewModelScope.launch(dispatchers.io) {
            userRepo.checkEmail(email)
                .onSuccess { isChecked: Boolean ->
                    _isEmailCheckSuccess.postValue(isChecked)
                }
                .onFailure { exception: Throwable ->
                    _exception.postValue(exception)
                }
        }
    }
}