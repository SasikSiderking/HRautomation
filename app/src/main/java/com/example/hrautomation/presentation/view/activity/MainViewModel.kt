package com.example.hrautomation.presentation.view.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val dispatchers: CoroutineDispatchers
) : BaseViewModel() {

    val filterMenuIconDrawableResId: LiveData<Int?>
        get() = _filterMenuIconDrawableResId
    private val _filterMenuIconDrawableResId: MutableLiveData<Int?> = MutableLiveData()

    init {
        sendNotificationToken()
    }

    fun logout() {
        viewModelScope.tryLaunch(
            dispatchers.io,
            doOnLaunch = {
                tokenRepository.logout()
            },
            doOnError = {
                _exception.postValue(it)
            }
        )
    }

    fun updateFilterIcon(drawableResId: Int?) {
        _filterMenuIconDrawableResId.postValue(drawableResId)
    }

    private fun sendNotificationToken() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                tokenRepository.sendNotificationToken()
            },
            doOnError = {
                Timber.e(it)
                _exception.postValue(it)
            }
        )
    }
}