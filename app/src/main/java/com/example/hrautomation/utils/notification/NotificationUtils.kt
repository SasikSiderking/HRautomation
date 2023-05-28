package com.example.hrautomation.utils.notification

import android.content.Context
import com.google.firebase.messaging.RemoteMessage

interface NotificationUtils {
    fun showNotification(message: RemoteMessage, context: Context)

    fun createNotificationChannel(context: Context)
}