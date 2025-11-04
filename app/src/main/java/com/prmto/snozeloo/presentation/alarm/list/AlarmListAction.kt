package com.prmto.snozeloo.presentation.alarm.list

sealed interface AlarmListAction {
    data object OnClickAddAlarm : AlarmListAction
    data class OnClickAlarmItem(val alarmId: String) : AlarmListAction
    data class ChangeAlarmEnabled(val alarmId: String, val isEnabled: Boolean) : AlarmListAction
}