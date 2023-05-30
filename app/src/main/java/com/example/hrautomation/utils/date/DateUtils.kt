package com.example.hrautomation.utils.date

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    const val PATTERN = "dd.MM.yyyy"

    private const val DAY_MONTH_PATTERN = "d MMMM"

    private const val DAY_MONTH_LOCALE_PATTERN = "d MMMM, HH:mm X"

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat(PATTERN, Locale("ru", "RU"))
        return formatter.format(date)
    }

    fun formatDateToDayMonth(date: Date): String {
        val formatter = SimpleDateFormat(DAY_MONTH_PATTERN, Locale("ru", "RU"))
        return formatter.format(date)
    }

    fun formatDateToDayMonthAndLocale(date: Date): String {
        val formatter = SimpleDateFormat(DAY_MONTH_LOCALE_PATTERN, Locale("ru", "RU"))
        return formatter.format(date)
    }

    fun parseDate(string: String): DateTime {
        val formatter = DateTimeFormat.forPattern(PATTERN)
        return formatter.parseDateTime(string)
    }
}