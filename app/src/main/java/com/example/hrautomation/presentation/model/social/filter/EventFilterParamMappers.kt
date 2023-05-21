package com.example.hrautomation.presentation.model.social.filter

import com.example.hrautomation.domain.model.social.filter.Filter

fun EventFilterParam.toFilter(): Filter {
    return Filter(
        name, fromDate, toDate, city?.id, format?.value
    )
}