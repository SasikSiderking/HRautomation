package com.example.hrautomation.utils.publisher

import com.example.hrautomation.presentation.model.social.EventFilterParam
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventFilterPublisher {
    val _eventFilterEventFlow: MutableSharedFlow<EventFilterEvent> = MutableSharedFlow()
    val eventFilterEventFlow: SharedFlow<EventFilterEvent> = _eventFilterEventFlow.asSharedFlow()
}

sealed class EventFilterEvent {
    class ProfileEventFilter(val eventFilterParam: EventFilterParam) : EventFilterEvent()
}