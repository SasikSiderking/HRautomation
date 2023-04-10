package com.example.hrautomation.presentation.view.social.filter

import androidx.lifecycle.viewModelScope
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.social.EventFilterParam
import com.example.hrautomation.utils.publisher.Event
import com.example.hrautomation.utils.publisher.Publisher
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventFilterViewModel @Inject constructor(private val publisher: Publisher) : BaseViewModel() {
    private var eventFilterParam: EventFilterParam = EventFilterParam(null, null, null)

    fun setDateFilter(localDate: String) {
        eventFilterParam = eventFilterParam.copy(date = localDate)
    }

    fun sendFilterParam() {
        viewModelScope.launch {
            publisher._eventFlow.emit(Event.EventFilter(eventFilterParam))
        }
    }
}