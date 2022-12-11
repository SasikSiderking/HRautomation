package com.example.hrautomation.presentation.base.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel() {

    val exception: LiveData<Throwable?>
        get() = _exception
    protected val _exception = MutableLiveData<Throwable?>()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}