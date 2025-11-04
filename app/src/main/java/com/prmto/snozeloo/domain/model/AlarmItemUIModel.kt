package com.prmto.snozeloo.domain.model

data class AlarmItemUIModel(
    val id: String,
    val title: String,
    val time: String,
    val isEnabled: Boolean,
    val repeatingDays: Set<DayValue>,
    val nextOccurrenceAlarmTime: String,
    val isVibrationEnabled: Boolean,
    val alarmVolume: Float,
    val alarmRingtone: String
)

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
