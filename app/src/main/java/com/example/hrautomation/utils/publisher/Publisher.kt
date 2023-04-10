package com.example.hrautomation.utils.publisher

import com.example.hrautomation.presentation.model.social.EventFilterParam
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Publisher {
    val _eventFlow: MutableSharedFlow<Event> = MutableSharedFlow()
    val eventFlow: SharedFlow<Event> = _eventFlow.asSharedFlow()
}

sealed class Event {
    object Update : Event()
    class EventFilter(val eventFilterParam: EventFilterParam) : Event()
}