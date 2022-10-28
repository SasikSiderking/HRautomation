package com.example.hrautomation.presentation.view.loading.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CodeLoginViewModel @Inject constructor(private val repo: TokenRepository, private val userRepo: UserRepository) : ViewModel() {

    val isCodeCheckSuccess: LiveData<Boolean>
        get() = _isCodeCheckSuccess
    private val _isCodeCheckSuccess = MutableLiveData<Boolean>()

    fun checkCode(email: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = userRepo.confirmEmail(email, code)
            if (token.isNotEmpty()) {
                _isCodeCheckSuccess.postValue(true)
                repo.saveToken(token)
            } else {
                _isCodeCheckSuccess.postValue(false)
            }
        }
    }
}