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
import javax.inject.Inject


class NotificationUtils @Inject constructor(
    private val stringResourceProvider: StringResourceProvider,
    private val context: Context
) {

    init {
        createNotificationChannel()
    }

    fun showNotification(title: String?, message: String?, eventId: Long?) {

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_hr_splash)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (eventId != null) {
            val intent = EventDetailsActivity.createIntent(context, eventId)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(pendingIntent)

            //        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, builder.build())
            with(NotificationManagerCompat.from(context)) {
                notify(eventId.toInt(), builder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = stringResourceProvider.getString(R.string.channel_name)
        val descriptionText = stringResourceProvider.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(PRIMARY_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val PRIMARY_CHANNEL_ID = "primary channel id"
    }
}