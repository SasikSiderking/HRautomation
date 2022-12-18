package com.example.hrautomation.presentation.view.loading.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.tryLaunch
import javax.inject.Inject

class EmailLoginViewModel @Inject constructor(
    private val tokenRepo: TokenRepository,
    private val dispatchers: CoroutineDispatchers
) : BaseViewModel() {

    val isEmailCheckSuccess: LiveData<Boolean>
        get() = _isEmailCheckSuccess
    private val _isEmailCheckSuccess = MutableLiveData<Boolean>()

    fun checkEmail(email: String) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                tokenRepo.checkEmail(email)
                _isEmailCheckSuccess.postValue(true)
            },
            doOnError = { error ->
                _exception.postValue(error)
                _isEmailCheckSuccess.postValue(false)
            }
        )
    }
}