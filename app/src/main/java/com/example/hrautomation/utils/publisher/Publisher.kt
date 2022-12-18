package com.example.hrautomation.utils.publisher

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Publisher {
    val _eventFlow: MutableStateFlow<Event> = MutableStateFlow(Event.None)
    val eventFlow: StateFlow<Event> = _eventFlow.asStateFlow()
}

sealed class Event {
    object Update : Event()
    object None : Event()
}