package com.example.hrautomation.presentation.view.loading.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.ITokenRepository
import com.example.hrautomation.domain.repository.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CodeLoginViewModel @Inject constructor(private val repo: ITokenRepository, private val userRepo: IUserRepository) : ViewModel() {

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