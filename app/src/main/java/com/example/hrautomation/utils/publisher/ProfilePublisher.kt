package com.example.hrautomation.utils.publisher

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ProfilePublisher {
    val _profileEventFlow: MutableSharedFlow<ProfileEvent> = MutableSharedFlow()
    val profileEventFlow: SharedFlow<ProfileEvent> = _profileEventFlow.asSharedFlow()
}

sealed class ProfileEvent {
    object Update : ProfileEvent()
}