package com.prmto.snozeloo.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prmto.snozeloo.domain.repository.AlarmRepository
import com.prmto.snozeloo.notification.AlarmNotifier
import com.prmto.snozeloo.presentation.alarm.ringtone.playmanager.RingtoneMediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmNotifier: AlarmNotifier

    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var ringtonePlayer: RingtoneMediaPlayer

    override fun onReceive(context: Context, intent: Intent) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        val alarmId = intent.getStringExtra(AndroidAlarmScheduler.ALARM_ITEM_ID)
        alarmNotifier.showNotification(intent)

        coroutineScope.launch {
            if (alarmId == null) return@launch
            val alarmItem = alarmRepository.getAlarmById(alarmId)
            ringtonePlayer.play(
                context,
                alarmItem?.alarmRingtoneUri,
                alarmItem?.alarmVolume
            )
        }
    }
}
