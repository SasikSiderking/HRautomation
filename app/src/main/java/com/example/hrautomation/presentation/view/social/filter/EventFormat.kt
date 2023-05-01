package com.example.hrautomation.presentation.view.social.filter

import com.example.hrautomation.R

enum class EventFormat(val eventType: Int, val value: String?) {
    ONLINE(R.string.event_format_online, "online"),
    OFFLINE(R.string.event_format_offline, "offline"),
    COMBINE(R.string.event_format_combine, "combine"),
    ANY(R.string.event_format_any, null);
}