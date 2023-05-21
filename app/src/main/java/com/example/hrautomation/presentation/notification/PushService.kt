package com.example.hrautomation.presentation.notification

import com.example.hrautomation.utils.notification.NotificationUtils
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import javax.inject.Inject

class PushService @Inject constructor(private val notificationUtils: NotificationUtils) : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            val newToken = task.result
            Timber.e("Abobus: $newToken")
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title: String? = message.data["title"]
        val text: String? = message.data["body"]
        val eventId = message.data["eventId"]?.toLong()
        notificationUtils.showNotification(title, text, eventId)
    }
}