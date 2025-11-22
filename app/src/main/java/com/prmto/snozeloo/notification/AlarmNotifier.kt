package com.prmto.snozeloo.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.prmto.snozeloo.R
import com.prmto.snozeloo.reminder.ReminderActivity
import com.prmto.snozeloo.data.alarm_manager.AndroidAlarmScheduler
import javax.inject.Inject

class AlarmNotifier @Inject constructor(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun createPendingIntent(intent: Intent?): PendingIntent {
        val alarmItemId = intent?.getStringExtra(AndroidAlarmScheduler.ALARM_ITEM_ID)
        val activityIntent = Intent(context, ReminderActivity::class.java).apply {
            putExtra(AndroidAlarmScheduler.ALARM_ITEM_ID, alarmItemId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            alarmItemId.hashCode(),
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return pendingIntent
    }

    fun showNotification(intent: Intent) {
        createChannel()
        val pendingIntent = createPendingIntent(intent)
        notificationManager.notify(
            intent.getStringExtra(AndroidAlarmScheduler.ALARM_ITEM_ID).hashCode(),
            buildNotification(pendingIntent)
        )
    }

    private fun buildNotification(pendingIntent: PendingIntent? = null): Notification {
        return NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("Alarm")
            .setContentText("Your alarm is ringing!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)
            .build()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_CHANNEL_ID,
                "Alarm Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val ALARM_CHANNEL_ID = "alarm_channel"
    }
}