package com.space.speedysensors.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.space.speedysensors.R


class PushNotifcationService {

    companion object {
        private val TAG: String = PushNotifcationService::class.java.simpleName

        val instance = PushNotifcationService()
    }


    private val channelId: String = "Alerts"
    private val channelDesc: String = "Get notified when a responder exhibits an anomaly."

    fun show(ctx: Context, title: String, content: String) {
        createNotificationChannel(ctx)
        val builder = NotificationCompat.Builder(ctx, "1")
        builder.setSmallIcon(android.R.drawable.stat_notify_error)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        builder.setVibrate(longArrayOf(0, 40000, 200, 40000))
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setChannelId(channelId)
        with(NotificationManagerCompat.from(ctx)) {
            notify(69420, builder.build())
        }
    }

    private fun createNotificationChannel(ctx:Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = Uri.parse("android.resource://${ctx.packageName}/${R.raw.alert}")
            val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelId, importance).apply {
                description = channelDesc
                setSound(soundUri, attributes)
            }
            val notificationManager: NotificationManager = ctx.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}