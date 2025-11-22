package com.prmto.snozeloo.reminder

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.R
import com.prmto.snozeloo.core.util.ObserveAsEvents
import com.prmto.snozeloo.data.alarm_manager.AndroidAlarmScheduler
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableSet

@AndroidEntryPoint
class ReminderActivity : ComponentActivity() {

    private val viewModel: ReminderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOverLockscreen()
        actionBar?.hide()
        enableEdgeToEdge()
        intent.getStringExtra(AndroidAlarmScheduler.Companion.ALARM_ITEM_ID)
        viewModel.getAlarmItem(intent.getStringExtra(AndroidAlarmScheduler.Companion.ALARM_ITEM_ID))
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