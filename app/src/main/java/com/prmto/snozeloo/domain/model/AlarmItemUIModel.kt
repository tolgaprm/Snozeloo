package com.prmto.snozeloo.domain.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import java.util.Calendar


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
    val alarmRingtoneUri: String,
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
    val currentTime = Calendar.getInstance()
    val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentTime.get(Calendar.MINUTE)
    return AlarmItemUIModel(
        id = "",
        title = "",
        timeHour = currentHour.toString(),
        timeMinute = currentMinute.toString(),
        alarmRingtoneUri = "Default",
        alarmVolume = 5f,
        isVibrationEnabled = false,
        repeatingDays = persistentSetOf(),
        nextOccurrenceAlarmTime = "",
        isActive = true
    )
}

fun DayValue.toCalendarDay(): Int {
    return when (this) {
        DayValue.SUNDAY -> Calendar.SUNDAY
        DayValue.MONDAY -> Calendar.MONDAY
        DayValue.TUESDAY -> Calendar.TUESDAY
        DayValue.WEDNESDAY -> Calendar.WEDNESDAY
        DayValue.THURSDAY -> Calendar.THURSDAY
        DayValue.FRIDAY -> Calendar.FRIDAY
        DayValue.SATURDAY -> Calendar.SATURDAY
    }
}