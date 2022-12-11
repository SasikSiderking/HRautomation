package com.example.hrautomation.presentation.view.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadingActivityViewModel @Inject constructor(private val tokenRepo: TokenRepository) : BaseViewModel() {

    val isTokenExist: LiveData<Boolean>
        get() = _isTokenExist
    private val _isTokenExist = MutableLiveData(false)

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            val token = tokenRepo.getAccessToken()
            _isTokenExist.postValue(token != null)
        }
    }
}