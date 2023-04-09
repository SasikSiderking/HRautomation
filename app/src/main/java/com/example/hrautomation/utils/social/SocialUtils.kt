package com.example.hrautomation.utils.social

import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.PeriodType
import java.util.*

object SocialUtils {
    fun checkForOngoing(eventDate: Date): Boolean {
        val eventDateTime = DateTime(eventDate)
        val currentDateTime = DateTime()
        val difference = Period(currentDateTime, eventDateTime, PeriodType.hours()).hours
        return difference > 0
    }
}