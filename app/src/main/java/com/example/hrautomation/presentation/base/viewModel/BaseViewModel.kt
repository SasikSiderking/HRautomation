package com.example.hrautomation.presentation.base.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    val exception: LiveData<Throwable?>
        get() = _exception
    protected val _exception = MutableLiveData<Throwable?>()

    protected var jobs: MutableList<Job> = mutableListOf()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    override fun onCleared() {
        jobs.forEach { job -> job.cancel() }
        super.onCleared()
    }
}