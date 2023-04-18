package com.example.hrautomation.presentation.view.social.filter

import androidx.lifecycle.viewModelScope
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.social.EventFilterParam
import com.example.hrautomation.utils.publisher.EventFilterEvent
import com.example.hrautomation.utils.publisher.EventFilterPublisher
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventFilterViewModel @Inject constructor(private val eventFilterPublisher: EventFilterPublisher) : BaseViewModel() {
    private var eventFilterParam: EventFilterParam = EventFilterParam(null, null, null, null)

    fun setDateFilter(date: String) {
        eventFilterParam = eventFilterParam.copy(date = date)
    }

    fun setNameFilter(name: String) {
        eventFilterParam = eventFilterParam.copy(name = name)
    }

    fun sendFilterParam() {
        viewModelScope.launch {
            eventFilterPublisher.emitEvent(EventFilterEvent.ProfileEventFilter(eventFilterParam))
        }
    }
}