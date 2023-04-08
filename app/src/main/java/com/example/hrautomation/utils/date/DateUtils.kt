package com.example.hrautomation.utils.date

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    fun formatDate(date: Date): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val localDate = Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.format(formatter)
    }
}