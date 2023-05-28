package com.example.hrautomation.utils.publisher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Publisher<T> {
    private val _mutableEventFlow: MutableSharedFlow<T> = MutableSharedFlow()
    val eventFlow: Flow<T> = _mutableEventFlow.asSharedFlow()

    suspend fun emitEvent(event: T) {
        _mutableEventFlow.emit(event)
    }
}