package com.example.hrautomation.presentation.view.social.filter

enum class EventFormat(val eventType: String) {
    ONLINE("online"),
    OFFLINE("offline"),
    COMBINE("combine");

    override fun toString(): String {
        return eventType
    }
}