package com.example.hrautomation.presentation.view.social.filter

import com.example.hrautomation.R

enum class EventFormat(val eventType: Int, val value: String?) {
    ONLINE(R.string.event_format_online, "ONLINE"),
    OFFLINE(R.string.event_format_offline, "OFFLINE"),
    COMBINE(R.string.event_format_combine, "COMBINED"),
    ANY(R.string.event_format_any, null);
}