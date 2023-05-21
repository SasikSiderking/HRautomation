package com.example.hrautomation.utils.publisher

import com.example.hrautomation.presentation.model.social.filter.EventFilterParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventFilterPublisher {
    private val _eventFilterEventFlow: MutableSharedFlow<EventFilterEvent> = MutableSharedFlow()
    val eventFilterEventFlow: Flow<EventFilterEvent> = _eventFilterEventFlow.asSharedFlow()

    suspend fun emitEvent(event: EventFilterEvent) {
        _eventFilterEventFlow.emit(event)
    }
}

sealed class EventFilterEvent {
    class ProfileEventFilter(val eventFilterParam: EventFilterParam) : EventFilterEvent()
}