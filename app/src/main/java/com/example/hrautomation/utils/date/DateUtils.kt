package com.example.hrautomation.utils.date

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    const val PATTERN = "dd.MM.yyyy"

    const val READABLE_PATTERN = "dd MMMM"

    const val READABLE_PATTERN_TIME = "dd MMMM, HH:mm z"

    fun formatDate(date: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale("ru", "RU"))
        return formatter.format(date)
    }

    fun parseDate(string: String): DateTime {
        val formatter = DateTimeFormat.forPattern(PATTERN)
        return formatter.parseDateTime(string)
    }
}