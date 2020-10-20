package com.test.myapplication.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.test.myapplication.MainActivity
import com.test.myapplication.R

class NotificationService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationBuilder()
    }

    private fun notificationBuilder() {
        val NOTIFICATION_CHANNEL_ID = "default_id"
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notificationBuilder: NotificationCompat.Builder
                = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setDefaults(Notification.DEFAULT_ALL)
                setContentTitle("notification title")
                setContentText("notification content")
                setAutoCancel(false)
                setWhen(System.currentTimeMillis())
                priority = NotificationCompat.PRIORITY_MAX
                setContentIntent(pendingIntent)
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_NONE).apply {
                description = getString(R.string.app_name)
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(notificationChannel)
        }

        startForeground(1, notificationBuilder.build())
    }
}