package com.example.hrautomation.presentation.view.social.filter

import androidx.lifecycle.viewModelScope
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.social.EventFilterParam
import com.example.hrautomation.presentation.model.social.EventFormat
import com.example.hrautomation.utils.publisher.EventFilterEvent
import com.example.hrautomation.utils.publisher.EventFilterPublisher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class EventFilterViewModel @Inject constructor(private val eventFilterPublisher: EventFilterPublisher) : BaseViewModel() {
    private var eventFilterParam: EventFilterParam = EventFilterParam(null, null, null, null, null)

    fun setFromDateFilter(date: Date?) {
        eventFilterParam = eventFilterParam.copy(fromDate = date)
    }

    fun setToDateFilter(date: Date?) {
        eventFilterParam = eventFilterParam.copy(toDate = date)
    }

    fun setNameFilter(name: String?) {
        eventFilterParam = eventFilterParam.copy(name = name)
    }

    fun setCityFilter(cityId: Long?) {
        eventFilterParam = eventFilterParam.copy(cityId = cityId)
    }

    fun setFormatFilter(format: EventFormat?) {
        eventFilterParam = eventFilterParam.copy(format = format)
    }

    fun sendFilterParam() {
        viewModelScope.launch {
            eventFilterPublisher.emitEvent(EventFilterEvent.ProfileEventFilter(eventFilterParam))
        }
    }
}