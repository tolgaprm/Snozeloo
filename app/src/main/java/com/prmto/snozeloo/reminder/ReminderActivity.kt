package com.prmto.snozeloo.reminder

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.prmto.snozeloo.core.util.ObserveAsEvents
import com.prmto.snozeloo.core.util.getAlarmItemAndIsSnooze
import com.prmto.snozeloo.presentation.alarm.ringtone.playmanager.RingtoneMediaPlayer
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import com.prmto.snozeloo.util.VibrationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderActivity : ComponentActivity() {

    private val viewModel: ReminderViewModel by viewModels()

    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var ringtonePlayer: RingtoneMediaPlayer

    @Inject
    lateinit var vibrationHelper: VibrationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOverLockscreen()
        actionBar?.hide()
        enableEdgeToEdge()
        val alarmIntentExt = intent.getAlarmItemAndIsSnooze()
        viewModel.getAlarmItem(alarmIntentExt)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(alarmIntentExt.alarmItemId?.hashCode() ?: 0)
        setContent {
            val alarmItem = viewModel.alarmItemState.value
            SnozelooTheme {
                AlarmTriggerScreen(
                    alarmItemUIModel = alarmItem,
                    onAction = viewModel::onAction
                )
            }

            ObserveAsEvents(
                viewModel.viewEvent
            ) { viewEvents ->
                when (viewEvents) {
                    is ReminderViewEvent.TurnOffAlarm -> {
                        // ringtone stop
                        this.finish()
                        ringtonePlayer.stop()
                        vibrationHelper.stopVibration()
                        notificationManager.cancel(alarmIntentExt.alarmItemId?.hashCode() ?: 0)
                    }
                }
            }
        }
    }

    private fun showOverLockscreen() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
    }
}