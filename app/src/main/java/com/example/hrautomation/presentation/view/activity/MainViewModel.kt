package com.example.hrautomation.presentation.view.activity

import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val tokenRepository: TokenRepository) : BaseViewModel() {
    fun logout() {
        with(tokenRepository) {
            setAccessToken(null)
            setRefreshToken(null)
        }
    }
}