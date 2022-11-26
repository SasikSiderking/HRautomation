package com.example.hrautomation.presentation.view.loading.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.Token
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CodeLoginViewModel @Inject constructor(
    private val tokenRepo: TokenRepository,
    private val userRepo: UserRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    val isCodeCheckSuccess: LiveData<Boolean>
        get() = _isCodeCheckSuccess
    private val _isCodeCheckSuccess = MutableLiveData<Boolean>()

    val exception: LiveData<Throwable?>
        get() = _exception
    private var _exception = MutableLiveData<Throwable?>()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    fun checkCode(email: String, code: String) {
        viewModelScope.launch(dispatchers.io) {
            userRepo.confirmEmail(email, code)
                .onSuccess { token: Token ->
                    _isCodeCheckSuccess.postValue(true)
                    with(tokenRepo) {
                        saveAccessToken(token.accessToken)
                        saveRefreshToken(token.refreshToken)
                        saveUserId(token.userId)
                    }
                }
                .onFailure { exception: Throwable ->
                    _isCodeCheckSuccess.postValue(false)
                    _exception.postValue(exception)
                    Timber.e(exception)
                }
        }
    }
}