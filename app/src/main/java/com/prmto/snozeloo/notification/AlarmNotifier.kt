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
import com.prmto.snozeloo.core.util.getAlarmItemAndIsSnooze
import com.prmto.snozeloo.core.util.putAlarmItem
import com.prmto.snozeloo.data.alarm_manager.AndroidAlarmScheduler
import com.prmto.snozeloo.data.alarm_manager.NotificationActionReceiver
import com.prmto.snozeloo.reminder.ReminderActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(intent: Intent) {
        createChannel()
        notificationManager.notify(
            intent.getStringExtra(AndroidAlarmScheduler.ALARM_ITEM_ID).hashCode(),
            buildNotification(intent)
        )
    }

    private fun buildNotification(intent: Intent): Notification {
        return NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("Alarm")
            .setContentText("Your alarm is ringing!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .addAction(
                R.drawable.alarm,
                "Turn off alarm",
                turnOffAlarmPendingIntent(intent)
            )
            .addAction(
                R.drawable.alarm,
                "Snooze for 5 minutes",
                snoozeAlarmPendingIntent(intent)
            )
            .setSilent(true)
            .setFullScreenIntent(createPendingIntent(intent), true)
            .build()
    }

    private fun createPendingIntent(intent: Intent?): PendingIntent {
        val alarmIntentExt = intent.getAlarmItemAndIsSnooze()

        val activityIntent = Intent(context, ReminderActivity::class.java).apply {
            putAlarmItem(alarmIntentExt = alarmIntentExt)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            alarmIntentExt.alarmItemId.hashCode(),
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return pendingIntent
    }

    private fun turnOffAlarmPendingIntent(intent: Intent?): PendingIntent {
        val turnOffAlarmIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            putAlarmItem(intent.getAlarmItemAndIsSnooze())
            action = TURN_OFF_ALARM_ACTION
        }

        return PendingIntent.getBroadcast(
            context,
            TURN_OFF_ALARM_REQUEST_CODE,
            turnOffAlarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun snoozeAlarmPendingIntent(intent: Intent?): PendingIntent {
        val snoozeAlarmIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            putAlarmItem(intent.getAlarmItemAndIsSnooze())
            action = SNOOZE_ALARM_ACTION
        }

        return PendingIntent.getBroadcast(
            context,
            SNOOZE_ALARM_REQUEST_CODE,
            snoozeAlarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
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
        const val TURN_OFF_ALARM_ACTION = "TURN_OFF_ALARM_ACTION"
        const val SNOOZE_ALARM_ACTION = "SNOOZE_ALARM_ACTION"
        const val TURN_OFF_ALARM_REQUEST_CODE = 105
        const val SNOOZE_ALARM_REQUEST_CODE = 106
    }
}