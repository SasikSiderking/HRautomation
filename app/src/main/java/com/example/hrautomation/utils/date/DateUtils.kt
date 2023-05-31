package com.example.hrautomation.utils.date

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    private const val INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

    const val PATTERN = "dd.MM.yyyy"

    private const val DAY_MONTH_PATTERN = "d MMMM"

    private const val DAY_MONTH_TIME_PATTERN = "d MMMM, HH:mm"

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat(PATTERN, Locale("ru", "RU"))
        return formatter.format(date)
    }

    fun formatDateToDayMonth(date: Date): String {
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Europe/Moscow"))
        val formatter = DateTimeFormatter.ofPattern(DAY_MONTH_PATTERN, Locale("ru", "RU"))
        return zonedDateTime.format(formatter)
    }

    fun formatDateToDayMonthAndLocale(date: Date): String {
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Europe/Moscow"))
        val formatter = DateTimeFormatter.ofPattern(DAY_MONTH_TIME_PATTERN, Locale("ru", "RU"))
        return zonedDateTime.format(formatter) + " МСК"
    }

    fun parseDate(string: String): Date {
        val isoFormat = SimpleDateFormat(INPUT_PATTERN, Locale("ru", "RU"))
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = isoFormat.parse(string)
        if (date != null) {
            return date
        } else {
            throw IllegalStateException("Unparsable date")
        }
    }
}