package com.example.hrautomation.presentation.view.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.publisher.ProfileEvent
import com.example.hrautomation.utils.publisher.ProfilePublisher
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val profilePublisher: ProfilePublisher,
    private val dispatchers: CoroutineDispatchers
) : BaseViewModel() {

    val filterMenuIconDrawableResId: LiveData<Int?>
        get() = _filterMenuIconDrawableResId
    private val _filterMenuIconDrawableResId: MutableLiveData<Int?> = MutableLiveData()

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

    fun updateColleagues() {
        viewModelScope.launch {
            profilePublisher.emitEvent(ProfileEvent.Update)
        }
    }

    fun updateFilterIcon(drawableResId: Int?) {
        _filterMenuIconDrawableResId.postValue(drawableResId)
    }
}