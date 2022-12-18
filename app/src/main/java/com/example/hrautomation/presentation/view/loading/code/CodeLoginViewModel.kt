package com.example.hrautomation.presentation.view.loading.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class CodeLoginViewModel @Inject constructor(
    private val tokenRepo: TokenRepository,
    private val dispatchers: CoroutineDispatchers
) : BaseViewModel() {

    val isCodeCheckSuccess: LiveData<Boolean>
        get() = _isCodeCheckSuccess
    private val _isCodeCheckSuccess = MutableLiveData<Boolean>()

    fun checkCode(email: String, code: String) {
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.io,
                doOnLaunch = {
                    val token = tokenRepo.confirmEmail(email, code)
                    _isCodeCheckSuccess.postValue(true)
                    with(tokenRepo) {
                        setAccessToken(token.accessToken)
                        setRefreshToken(token.refreshToken)
                        setUserId(token.userId)
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