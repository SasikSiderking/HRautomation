package com.example.hrautomation.presentation.view.loading.activity_load

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.ITokenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadingActivityViewModel @Inject constructor(private val repo: ITokenRepository) : ViewModel() {
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData(true)

    val isTokenExist: LiveData<Boolean>
        get() = _isTokenExist
    private val _isTokenExist = MutableLiveData(false)

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            delay(1000)
            findToken()
        }
    }

    private fun findToken() {
        val token = repo.getToken()
        token?.let {
            _isTokenExist.postValue(true)
            _isLoading.postValue(false)
        } ?: apply {
            _isTokenExist.postValue(false)
            _isLoading.postValue(false)
        }
    }
}