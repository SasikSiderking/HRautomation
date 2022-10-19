package com.example.hrautomation.presentation.view.login_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hrautomation.domain.repository.IUserRepository
import javax.inject.Inject

class LoginDialogViewModelProvider @Inject constructor(private val repo: IUserRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginDialogViewModel(repo) as T
    }
}