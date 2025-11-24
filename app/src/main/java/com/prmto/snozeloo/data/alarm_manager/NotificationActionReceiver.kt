package com.prmto.snozeloo.data.alarm_manager

import DateUtil.ALARM_SNOOZE_TIME_IN_MINUTE
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prmto.snozeloo.core.util.getAlarmItemAndIsSnooze
import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
import com.prmto.snozeloo.notification.AlarmNotifier
import com.prmto.snozeloo.presentation.alarm.ringtone.playmanager.RingtoneMediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var ringtonePlayer: RingtoneMediaPlayer

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when (intent?.action) {
            AlarmNotifier.TURN_OFF_ALARM_ACTION -> {
                notificationManager.cancel(
                    intent.getAlarmItemAndIsSnooze().alarmItemId?.hashCode() ?: 0
                )
                ringtonePlayer.stop()
            }

            AlarmNotifier.SNOOZE_ALARM_ACTION -> {
                val alarmItemState = intent.getAlarmItemAndIsSnooze()
                alarmScheduler.scheduleAlarmForSnooze(
                    alarmItemId = alarmItemState.alarmItemId ?: "",
                    snoozeTimeInMinute = ALARM_SNOOZE_TIME_IN_MINUTE
                )
                notificationManager.cancel(alarmItemState.alarmItemId?.hashCode() ?: 0)
                ringtonePlayer.stop()
            }
        }
    }
}