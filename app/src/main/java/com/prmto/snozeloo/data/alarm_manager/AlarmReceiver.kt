package com.prmto.snozeloo.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prmto.snozeloo.notification.AlarmNotifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmNotifier: AlarmNotifier

    override fun onReceive(context: Context, intent: Intent) {
        alarmNotifier.showNotification(intent)
    }
}
