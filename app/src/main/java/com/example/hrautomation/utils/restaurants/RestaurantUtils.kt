package com.example.hrautomation.utils.restaurants

import kotlin.math.floor

object RestaurantUtils {

    private const val DIGIT = 10.0

    fun roundRating(rating: Float): String {
        return (floor(rating * DIGIT) / DIGIT).toString()
    }
}