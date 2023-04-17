package com.example.hrautomation.presentation.view.activity

import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.publisher.ProfileEvent
import com.example.hrautomation.utils.publisher.ProfilePublisher
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val profilePublisher: ProfilePublisher
) : BaseViewModel() {

    fun logout() {
        with(tokenRepository) {
            setAccessToken(null)
            setRefreshToken(null)
        }
    }

    fun updateColleagues() {
        viewModelScope.launch {
            profilePublisher.emitEvent(ProfileEvent.Update)
        }
    }
}