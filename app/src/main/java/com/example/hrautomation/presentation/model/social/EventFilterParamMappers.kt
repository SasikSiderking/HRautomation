package com.example.hrautomation.presentation.model.social

import com.example.hrautomation.domain.model.social.Filter

fun EventFilterParam.toFilter(): Filter {
    return Filter(
        name, fromDate, toDate, city?.id, format?.value
    )
}