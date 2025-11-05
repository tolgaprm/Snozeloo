package com.prmto.snozeloo.presentation.alarm.detail

import com.prmto.snozeloo.domain.model.DayValue


sealed interface AlarmDetailAction {
    data object OnClickClose : AlarmDetailAction
    data object OnClickSave : AlarmDetailAction
    data object OnClickAlarmRingtone : AlarmDetailAction
    data class OnClickedRepeatingDay(val day: DayValue): AlarmDetailAction
    data class OnChangedAlarmHour(val hour: String) : AlarmDetailAction
    data class OnChangedAlarmMinute(val minute: String) : AlarmDetailAction
    data class OnChangedAlarmTitle(val title: String) : AlarmDetailAction
    data class OnChangedAlarmVolume(val alarmVolume: Float) : AlarmDetailAction
    data class OnChangedAlarmVibrationEnabled(val isVibrationEnabled: Boolean) : AlarmDetailAction
}