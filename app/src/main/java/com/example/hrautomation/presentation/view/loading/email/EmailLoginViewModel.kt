package com.example.hrautomation.presentation.view.loading.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.utils.tryLaunch
import javax.inject.Inject

class EmailLoginViewModel @Inject constructor(private val userRepo: UserRepository, private val dispatchers: CoroutineDispatchers) : ViewModel() {

    val isEmailCheckSuccess: LiveData<Boolean>
        get() = _isEmailCheckSuccess
    private val _isEmailCheckSuccess = MutableLiveData<Boolean>()

    val exception: LiveData<Throwable?>
        get() = _exception
    private val _exception = MutableLiveData<Throwable?>()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    fun checkEmail(email: String) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                userRepo.checkEmail(email)
                _isEmailCheckSuccess.postValue(true)
            },
            doOnError = { error ->
                _exception.postValue(error)
                _isEmailCheckSuccess.postValue(false)
            }
        )
    }
}