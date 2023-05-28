package com.example.hrautomation.utils.notification

import com.example.hrautomation.app.App
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class PushService @Inject constructor() : FirebaseMessagingService() {

    @Inject
    lateinit var eventNotificationUtils: EventNotificationUtils

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).appComponent.inject(this)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        eventNotificationUtils.showNotification(message, applicationContext)
    }
}