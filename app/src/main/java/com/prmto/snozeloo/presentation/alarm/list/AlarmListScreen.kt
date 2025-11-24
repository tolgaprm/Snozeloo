package com.prmto.snozeloo.presentation.alarm.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.snozeloo.R
import com.prmto.snozeloo.core.util.ShowSectionsWithEmptyState
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.presentation.components.AlarmListItem
import com.prmto.snozeloo.presentation.components.EmptyAlarmSection
import com.prmto.snozeloo.presentation.theme.SnozelooTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun AlarmListScreen(
    alarms: ImmutableList<AlarmItemUIModel>?,
    onAction: (AlarmListAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(AlarmListAction.OnClickAddAlarm) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Alarm"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            Text(
                text = stringResource(R.string.your_alarms),
                style = MaterialTheme.typography.titleLarge
            )

            ShowSectionsWithEmptyState(
                data = alarms,
                onEmptyState = { EmptyAlarmSection() },
                onNotEmptyState = { AlarmListContent(alarms = it, onAction = onAction) }
            )
        }
    }
}


@Composable
private fun AlarmListContent(
    alarms: ImmutableList<AlarmItemUIModel>,
    onAction: (AlarmListAction) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(alarms) { alarmItem ->
            AlarmListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = true,
                        onClick = { onAction(AlarmListAction.OnClickAlarmItem(alarmItem.id)) }),
                alarmItemUIModel = alarmItem,
                onCheckedChange = {
                    onAction(
                        AlarmListAction.ChangeAlarmEnabled(
                            alarmId = alarmItem.id,
                            isEnabled = it
                        )
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlarmListScreenPreview() {
    SnozelooTheme {
        AlarmListScreen(
            alarms = persistentListOf(
                AlarmItemUIModel(
                    id = "1",
                    title = "Wake Up",
                    timeHour = "10",
                    timeMinute = "00",
                    repeatingDays = DayValue.entries.toImmutableSet(),
                    nextOccurrenceAlarmTime = "Alarm in 30 minutes",
                    isActive = false,
                    alarmVolume = 5f,
                    alarmRingtoneUri = "Default",
                    isVibrationEnabled = false
                ),
                AlarmItemUIModel(
                    id = "2",
                    title = "Education",
                    timeHour = "04",
                    timeMinute = "00",
                    repeatingDays = persistentSetOf(DayValue.MONDAY, DayValue.WEDNESDAY, DayValue.FRIDAY),
                    nextOccurrenceAlarmTime = "Alarm in 30 minutes",
                    isActive = true,
                    alarmVolume = 5f,
                    alarmRingtoneUri = "Default",
                    isVibrationEnabled = false,
                )
            ),
            onAction = {}
        )
    }
}