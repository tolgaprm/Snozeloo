package com.prmto.snozeloo.presentation.alarm.list

sealed interface AlarmListAction {
    data object OnClickAddAlarm : AlarmListAction
    data object OnClickAlarmItem : AlarmListAction
    data object ChangeAlarmEnabled : AlarmListAction
}