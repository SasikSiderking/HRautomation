package com.example.hrautomation.data.model.social.filter

import com.example.hrautomation.domain.model.social.filter.Filter


fun Filter.toEventFilter(): EventFilter {
    return EventFilter(
        name, fromDate, toDate, cityId, format
    )
}