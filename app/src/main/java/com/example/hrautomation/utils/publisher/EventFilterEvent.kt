package com.example.hrautomation.utils.publisher

import com.example.hrautomation.presentation.model.social.filter.EventFilterParam

sealed class EventFilterEvent {
    class ProfileEventFilter(val eventFilterParam: EventFilterParam) : EventFilterEvent()
}