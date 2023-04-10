package com.example.hrautomation.utils.social

import org.joda.time.DateTime
import java.util.*

object SocialUtils {
    fun checkForOngoing(eventDate: Date): Boolean {
        val eventDateTime = DateTime(eventDate)
        return eventDateTime.isAfterNow
    }
}