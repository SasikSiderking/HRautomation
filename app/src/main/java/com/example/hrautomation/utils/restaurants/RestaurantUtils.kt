package com.example.hrautomation.utils.restaurants

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor

object RestaurantUtils {

    private const val DIGIT = 10.0

    fun roundRating(rating: Float): String {
        return (" " + floor(rating * DIGIT) / DIGIT)
    }

    fun formatDate(date: Date): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val sm = Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
        return sm.format(formatter)
    }
}