package com.example.hrautomation.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.hrautomation.R
import com.example.hrautomation.presentation.view.social.details.EventDetailsActivity
import com.example.hrautomation.utils.resources.StringResourceProvider
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject


class EventNotificationUtils @Inject constructor(
    private val stringResourceProvider: StringResourceProvider,
    context: Context
) : NotificationUtils {

    init {
        createNotificationChannel(context)
    }

    override fun showNotification(message: RemoteMessage, context: Context) {

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_hr_splash)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val eventId = message.data[EventDetailsActivity.EVENT_ID_EXTRA]?.toLong()

        if (eventId != null) {
            val intent = EventDetailsActivity.createIntent(context, eventId)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            builder.setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(context)) {
                notify(eventId.toInt(), builder.build())
            }
        }
    }

    override fun createNotificationChannel(context: Context) {
        val name = stringResourceProvider.getString(R.string.event_channel_name)
        val descriptionText = stringResourceProvider.getString(R.string.event_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(PRIMARY_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(context, NotificationManager::class.java) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val PRIMARY_CHANNEL_ID = "primary channel id"
    }
}