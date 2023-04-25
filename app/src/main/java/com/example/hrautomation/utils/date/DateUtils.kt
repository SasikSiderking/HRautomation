package com.example.hrautomation.utils.date

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    private const val PATTERN = "dd.MM.yyyy"

    fun formatDate(date: Date): String {
        val formatter = DateTimeFormatter.ofPattern(PATTERN)
        val localDate = Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.format(formatter)
    }

    fun parseDate(string: String): DateTime {
        val formatter = DateTimeFormat.forPattern(PATTERN)
        return formatter.parseDateTime(string)
    }
}