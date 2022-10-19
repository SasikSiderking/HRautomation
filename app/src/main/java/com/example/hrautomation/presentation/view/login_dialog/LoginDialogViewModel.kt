package com.example.hrautomation.presentation.view.login_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginDialogViewModel constructor(private val repo: IUserRepository): ViewModel() {
    val tokenState: LiveData<Boolean>
        get() = _tokenState
    private val _tokenState = MutableLiveData<Boolean>()

    val uiState: LiveData<Boolean>
    get() = _uiState
    private val _uiState = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>(false)

    fun okEmailListener(email: String){
        viewModelScope.launch(Dispatchers.IO){
            _uiState.postValue(false)
            _isLoading.postValue(true)
            repo.checkEmail(email)
            _isLoading.postValue(false)
        }
    }
    fun okCodeListener(email: String, code: String){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.postValue(true)
            _isLoading.postValue(true)
            val token = repo.confirmEmail(email, code)
            repo.saveToken(token)
            _tokenState.postValue(true)
            _isLoading.postValue(false)
        }
    }
}