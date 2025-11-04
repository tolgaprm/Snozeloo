package com.prmto.snozeloo.presentation.alarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.theme.AlarmItemShape
import com.prmto.snozeloo.presentation.theme.Gray
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import com.prmto.snozeloo.presentation.theme.blue50

@Composable
fun AlarmListItem(
    modifier: Modifier = Modifier,
    alarmItemUIModel: AlarmItemUIModel,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .clip(AlarmItemShape)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alarmItemUIModel.title,
                style = MaterialTheme.typography.labelMedium
            )

            Switch(
                checked = alarmItemUIModel.isEnabled,
                colors = SwitchDefaults.colors(
                    uncheckedTrackColor = blue50,
                    uncheckedThumbColor = Color.White,
                    uncheckedBorderColor = blue50
                ),
                onCheckedChange = onCheckedChange
            )
        }

        Text(
            text = alarmItemUIModel.time,
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            text = alarmItemUIModel.nextOccurrenceAlarmTime,
            style = MaterialTheme.typography.labelSmall,
            color = Gray
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DayValue.entries.forEach { dayValue ->
                DayChip(
                    dayValue = dayValue,
                    isSelected = alarmItemUIModel.repeatingDays.contains(dayValue),
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}


@Preview
@Composable
private fun AlarmItemPreview() {
    SnozelooTheme {
        AlarmListItem(
            alarmItemUIModel = AlarmItemUIModel(
                id = "1",
                title = "Wake Up",
                time = "10:00",
                repeatingDays = setOf(DayValue.MONDAY, DayValue.WEDNESDAY, DayValue.FRIDAY),
                nextOccurrenceAlarmTime = "Alarm in 30 minutes",
                isEnabled = true,
                isVibrationEnabled = false,
                alarmVolume = 5f,
                alarmRingtone = "Default"
            ),
            onCheckedChange = {}
        )
    }
}