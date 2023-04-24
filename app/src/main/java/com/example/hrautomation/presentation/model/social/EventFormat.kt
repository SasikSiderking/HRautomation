package com.example.hrautomation.presentation.model.social

enum class EventFormat(val eventType: String) {
    ONLINE("online"),
    OFFLINE("offline"),
    COMBINE("combine");

    override fun toString(): String {
        return eventType
    }
}