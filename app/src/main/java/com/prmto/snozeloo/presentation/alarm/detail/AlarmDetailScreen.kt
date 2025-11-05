package com.prmto.snozeloo.presentation.alarm.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.R
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.alarm.components.DayChip
import com.prmto.snozeloo.presentation.alarm.components.SnoozelooSwitch
import com.prmto.snozeloo.presentation.alarm.detail.components.DetailColumnItemSection
import com.prmto.snozeloo.presentation.alarm.detail.components.DetailItemSection
import com.prmto.snozeloo.presentation.alarm.detail.components.DetailRowItemSection
import com.prmto.snozeloo.presentation.theme.Gray
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import com.prmto.snozeloo.presentation.theme.buttonColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmDetailScreen(
    alarmDetail: AlarmItemUIModel?,
) {
    alarmDetail ?: return
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    CloseButton(
                        enabled = false,
                        onClick = { }
                    )
                },
                actions = {
                    ActionButton(
                        enabled = true,
                        onClick = {}
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailItemSection {

            }

            DetailRowItemSection(
                title = stringResource(R.string.alarm_name)
            ) {
                Text(
                    text = alarmDetail.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray
                )
            }

            DetailColumnItemSection(
                title = stringResource(R.string.repeat)
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    DayValue.entries.forEach { dayValue ->
                        DayChip(
                            dayValue = dayValue,
                            isSelected = alarmDetail.repeatingDays.contains(dayValue),
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .clickable {

                                }
                        )
                    }
                }
            }

            DetailRowItemSection(
                title = "Alarm ringtone"
            ) {
                Text(
                    text = alarmDetail.alarmRingtone,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray
                )
            }

            DetailColumnItemSection(
                title = stringResource(R.string.alarm_volume)
            ) {
                Slider(
                    value = alarmDetail.alarmVolume,
                    onValueChange = { },
                    valueRange = 0f..10f,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            DetailRowItemSection(
                title = stringResource(R.string.vibrate)
            ) {
                SnoozelooSwitch(
                    checked = alarmDetail.isVibrationEnabled,
                    onCheckedChange = { }
                )
            }
        }
    }
}

@Composable
private fun CloseButton(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        enabled = enabled,
        colors = buttonColors(),
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
        )
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = buttonColors(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = stringResource(R.string.save)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlarmDetailScreenPreview() {
    SnozelooTheme {
        AlarmDetailScreen(
            AlarmItemUIModel(
                id = "2",
                title = "Work",
                time = "04:00",
                repeatingDays = setOf(
                    DayValue.MONDAY,
                    DayValue.TUESDAY,
                    DayValue.WEDNESDAY,
                    DayValue.FRIDAY
                ),
                nextOccurrenceAlarmTime = "Alarm in 30 minutes",
                isEnabled = true,
                alarmVolume = 5f,
                alarmRingtone = "Default",
                isVibrationEnabled = true,
            )
        )
    }
}