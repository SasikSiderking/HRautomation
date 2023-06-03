package com.example.hrautomation.utils.date

import com.example.hrautomation.R
import com.example.hrautomation.utils.resources.ConfigurationProvider
import com.example.hrautomation.utils.resources.StringResourceProvider
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    private const val INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

    private const val PATTERN = "dd.MM.yyyy"

    private const val DAY_MONTH_PATTERN = "d MMMM"

    private const val DAY_MONTH_TIME_PATTERN = "d MMMM, HH:mm"

    private const val ZONE_ID = "Europe/Moscow"


    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat(PATTERN, Locale.getDefault())
        return formatter.format(date)
    }

    fun formatDateToDayMonth(date: Date, configurationProvider: ConfigurationProvider): String {
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(ZONE_ID))
        val formatter =
            DateTimeFormatter.ofPattern(DAY_MONTH_PATTERN, configurationProvider.getConfiguration().locales.get(0))
        return zonedDateTime.format(formatter)
    }

    fun formatDateToDayMonthAndLocale(
        date: Date,
        configurationProvider: ConfigurationProvider,
        stringResourceProvider: StringResourceProvider
    ): String {
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(ZONE_ID))
        val formatter =
            DateTimeFormatter.ofPattern(DAY_MONTH_TIME_PATTERN, configurationProvider.getConfiguration().locales.get(0))
        return stringResourceProvider.getString(R.string.date_format_with_timezone, zonedDateTime.format(formatter))
    }

    fun parseDate(stringDate: String): Date {
        val isoFormat = SimpleDateFormat(INPUT_PATTERN, Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = isoFormat.parse(stringDate)
        return date ?: throw IllegalStateException("Unparsable date $stringDate")
    }
}