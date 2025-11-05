package com.prmto.snozeloo.presentation.alarm.list

sealed interface AlarmListViewEvent {
    data class NavigateToAlarmDetail(val alarmId: String?) : AlarmListViewEvent
}