package com.example.hrautomation.presentation.view.loading.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class CodeLoginViewModel @Inject constructor(
    private val tokenRepo: TokenRepository,
    private val userRepo: UserRepository,
    private val dispatchers: CoroutineDispatchers
) : BaseViewModel() {

    val isCodeCheckSuccess: LiveData<Boolean>
        get() = _isCodeCheckSuccess
    private val _isCodeCheckSuccess = MutableLiveData<Boolean>()

    fun checkCode(email: String, code: String) {
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.io,
                doOnLaunch = {
                    val token = userRepo.confirmEmail(email, code)
                    _isCodeCheckSuccess.postValue(true)
                    with(tokenRepo) {
                        saveAccessToken(token.accessToken)
                        saveRefreshToken(token.refreshToken)
                        saveUserId(token.userId)
                    }
                },
                doOnError = { error ->
                    _isCodeCheckSuccess.postValue(false)
                    _exception.postValue(error)
                    Timber.e(error)
                }
            )
    }
}