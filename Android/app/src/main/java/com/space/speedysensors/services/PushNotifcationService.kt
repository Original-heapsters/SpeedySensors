package com.space.speedysensors.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class PushNotifcationService {

    companion object {
        private val TAG: String = PushNotifcationService::class.java.simpleName

        val instance = PushNotifcationService()
    }


    private val channelId:String = "SPEEDYSENSORBOIS"
    private val channelDesc:String = "SPEEDYSENSORBOIS"

    fun show(ctx:Context, title:String, content:String){
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
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelId, importance).apply {
                description = channelDesc
            }
            val notificationManager: NotificationManager =
                ctx.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}