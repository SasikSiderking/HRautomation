package com.example.hrautomation.presentation.view.activity

import androidx.lifecycle.viewModelScope
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.utils.publisher.Event
import com.example.hrautomation.utils.publisher.Publisher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val publisher: Publisher) : BaseViewModel() {
    fun updateColleagues() {
        viewModelScope.launch {
            publisher._eventFlow.emit(Event.Update)
            delay(1000)
            publisher._eventFlow.emit(Event.None)
        }
    }
}