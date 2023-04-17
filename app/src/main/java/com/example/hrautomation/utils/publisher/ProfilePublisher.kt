package com.example.hrautomation.utils.publisher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ProfilePublisher {
    private val _profileEventFlow: MutableSharedFlow<ProfileEvent> = MutableSharedFlow()
    val profileEventFlow: Flow<ProfileEvent> = _profileEventFlow.asSharedFlow()

    suspend fun emitEvent(event: ProfileEvent) {
        _profileEventFlow.emit(event)
    }
}

sealed class ProfileEvent {
    object Update : ProfileEvent()
}