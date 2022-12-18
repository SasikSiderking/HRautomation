package com.example.hrautomation.utils.publisher

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Publisher {
    val _eventFlow: MutableSharedFlow<Event> = MutableStateFlow(Event.None)
    val eventFlow: SharedFlow<Event> = _eventFlow.asSharedFlow()
}

sealed class Event {
    object Update : Event()
    object None : Event()
}