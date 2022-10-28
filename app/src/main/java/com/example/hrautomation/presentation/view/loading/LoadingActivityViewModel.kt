package com.example.hrautomation.presentation.view.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.TokenRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadingActivityViewModel @Inject constructor(private val tokenRepo: TokenRepository) : ViewModel() {

    val isTokenExist: LiveData<Boolean>
        get() = _isTokenExist
    private val _isTokenExist = MutableLiveData(false)

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            val token = tokenRepo.getToken()
            _isTokenExist.postValue(token != null)
        }
    }
}