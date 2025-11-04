package com.prmto.snozeloo.presentation.alarm.detail

import com.prmto.snozeloo.domain.model.DayValue

sealed interface AlarmDetailAction {
    data class OnChangedAlarmHour(val hour: Int) : AlarmDetailAction
    data class OnChangedAlarmMinute(val minute: Int) : AlarmDetailAction
    data class OnChangedAlarmTitle(val title: String) : AlarmDetailAction
    data class OnChangedAlarmRepeatingDays(val repeatingDays: Set<DayValue>) : AlarmDetailAction
    data class OnChangedAlarmRingtone(val alarmRingtone: String) : AlarmDetailAction
    data class OnChangedAlarmVolume(val alarmVolume: Float) : AlarmDetailAction
    data class OnChangedAlarmVibrationEnabled(val isVibrationEnabled: Boolean) : AlarmDetailAction
}