package com.prmto.snozeloo.reminder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.R
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import com.prmto.snozeloo.presentation.theme.blue100
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun AlarmTriggerScreen(
    alarmItemUIModel: AlarmItemUIModel?,
    onAction: (ReminderAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (alarmItemUIModel == null) return
    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier.Companion
                .padding(32.dp)
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.alarm),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.Companion.height(16.dp))

            Text(
                text = alarmItemUIModel.time,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.Companion.height(16.dp))

            if (alarmItemUIModel.title.isNotEmpty()) {
                Text(
                    text = alarmItemUIModel.title,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.Companion.height(16.dp))
            }

            Button(
                onClick = {
                    onAction(ReminderAction.OnClickTurnOff)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.turn_off),
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Spacer(Modifier.Companion.height(16.dp))

            OutlinedButton(
                onClick = {
                    onAction(ReminderAction.OnClickSnooze)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = blue100,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.snooze_for_5_minutes),
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlarmTriggerScreenPreview() {
    SnozelooTheme {
        AlarmTriggerScreen(
            alarmItemUIModel = AlarmItemUIModel(
                id = "1",
                title = "Wake Up",
                timeHour = "10",
                timeMinute = "00",
                repeatingDays = DayValue.entries.toImmutableSet(),
                nextOccurrenceAlarmTime = "Alarm in 30 minutes",
                isActive = false,
                alarmVolume = 5f,
                alarmRingtoneUri = "Default",
                isVibrationEnabled = false,
                alarmRingtoneName = "Default"
            ),
            onAction = {}
        )
    }
}