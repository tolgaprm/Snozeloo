package com.prmto.snozeloo.presentation.alarm

sealed interface AlarmViewEvent {
    data class NavigateToAlarmDetail(val alarmId: String?) : AlarmViewEvent
}