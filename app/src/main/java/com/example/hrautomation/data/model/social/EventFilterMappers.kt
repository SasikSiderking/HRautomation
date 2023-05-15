package com.example.hrautomation.data.model.social

import com.example.hrautomation.domain.model.social.Filter


fun Filter.toEventFilter(): EventFilter {
    return EventFilter(
        name, fromDate, toDate, cityId, format
    )
}