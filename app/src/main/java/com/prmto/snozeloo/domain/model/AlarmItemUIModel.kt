package com.prmto.snozeloo.domain.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf


data class AlarmItemUIModel(
    val id: String,
    val title: String,
    val timeHour: String,
    val timeMinute: String,
    val isActive: Boolean,
    val repeatingDays: ImmutableSet<DayValue>,
    val nextOccurrenceAlarmTime: String,
    val isVibrationEnabled: Boolean,
    val alarmVolume: Float,
    val alarmRingtone: String
) {
    val time get() = "$timeHour : $timeMinute"
}

enum class DayValue {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    fun getDayName(): String {
        return name.take(3).lowercase().replaceFirstChar { it.uppercase() }
    }
}

fun defaultAlarmItemUiModel(): AlarmItemUIModel {
    return AlarmItemUIModel(
        id = "",
        title = "",
        timeHour = "00",
        timeMinute = "00",
        alarmRingtone = "Default",
        alarmVolume = 5f,
        isVibrationEnabled = false,
        repeatingDays = persistentSetOf(),
        nextOccurrenceAlarmTime = "",
        isActive = true
    )
}
