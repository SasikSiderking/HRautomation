package com.example.hrautomation.presentation.view.loading.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CodeLoginViewModel @Inject constructor(
    private val tokenRepo: TokenRepository,
    private val userRepo: UserRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    val isCodeCheckSuccess: LiveData<Boolean>
        get() = _isCodeCheckSuccess
    private val _isCodeCheckSuccess = MutableLiveData<Boolean>()

    fun checkCode(email: String, code: String) {
        viewModelScope.launch(dispatchers.io) {
            val token = userRepo.confirmEmail(email, code)
            if (token.isNotEmpty()) {
                _isCodeCheckSuccess.postValue(true)
                tokenRepo.saveToken(token)
            } else {
                _isCodeCheckSuccess.postValue(false)
            }
        }
    }
}