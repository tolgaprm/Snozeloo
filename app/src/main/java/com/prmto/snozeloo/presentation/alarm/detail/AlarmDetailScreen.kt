package com.prmto.snozeloo.presentation.alarm.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.R
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.alarm.detail.components.DetailColumnItemSection
import com.prmto.snozeloo.presentation.alarm.detail.components.DetailRowItemSection
import com.prmto.snozeloo.presentation.components.DayChip
import com.prmto.snozeloo.presentation.components.SnoozelooEditText
import com.prmto.snozeloo.presentation.components.SnoozelooSwitch
import com.prmto.snozeloo.presentation.theme.Gray
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import com.prmto.snozeloo.presentation.theme.buttonColors
import kotlinx.collections.immutable.persistentSetOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmDetailScreen(
    alarmDetail: AlarmItemUIModel,
    isSaveButtonEnable: Boolean,
    onAction: (AlarmDetailAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    CloseButton(
                        enabled = true,
                        onClick = {
                            onAction(AlarmDetailAction.OnClickClose)
                        }
                    )
                },
                actions = {
                    ActionButton(
                        enabled = isSaveButtonEnable,
                        onClick = {
                            onAction(AlarmDetailAction.OnClickSave)
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailColumnItemSection(title = null) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    SnoozelooEditText(
                        value = alarmDetail.timeHour,
                        onValueChange = {
                            onAction(AlarmDetailAction.OnChangedAlarmHour(it))
                        }
                    )

                    Text(
                        ":",
                        style = MaterialTheme.typography.displayMedium,
                        color = Gray
                    )

                    SnoozelooEditText(
                        value = alarmDetail.timeMinute,
                        onValueChange = {
                            onAction(AlarmDetailAction.OnChangedAlarmMinute(it))
                        }
                    )
                }
            }

            DetailRowItemSection(
                title = stringResource(R.string.alarm_name),
                onClickItem = {

                }
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
                                .padding(end = 5.dp),
                            onClick = {
                                onAction(AlarmDetailAction.OnClickedRepeatingDay(dayValue))
                            }
                        )
                    }
                }
            }

            DetailRowItemSection(
                title = stringResource(R.string.alarm_ringtone),
                onClickItem = {
                    onAction(AlarmDetailAction.OnClickAlarmRingtone)
                }
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
                    onValueChange = {
                        onAction(AlarmDetailAction.OnChangedAlarmVolume(it))
                    },
                    valueRange = 0f..10f,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            DetailRowItemSection(
                title = stringResource(R.string.vibrate)
            ) {
                SnoozelooSwitch(
                    checked = alarmDetail.isVibrationEnabled,
                    onCheckedChange = {
                        onAction(AlarmDetailAction.OnChangedAlarmVibrationEnabled(it))
                    }
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
            alarmDetail = AlarmItemUIModel(
                id = "",
                title = "",
                timeHour = "00",
                timeMinute = "00",
                alarmRingtone = "",
                alarmVolume = 5f,
                isVibrationEnabled = false,
                repeatingDays = persistentSetOf(),
                nextOccurrenceAlarmTime = "",
                isEnabled = true
            ),
            isSaveButtonEnable = false,
            onAction = {}
        )
    }
}