package com.example.hrautomation.presentation.view.loading.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmailLoginViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {

    val isEmailCheckSuccess: LiveData<Boolean>
        get() = _isEmailCheckSuccess
    private val _isEmailCheckSuccess = MutableLiveData<Boolean>()

    fun checkEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isEmailValid = repo.checkEmail(email)
            _isEmailCheckSuccess.postValue(isEmailValid)
        }
    }
}