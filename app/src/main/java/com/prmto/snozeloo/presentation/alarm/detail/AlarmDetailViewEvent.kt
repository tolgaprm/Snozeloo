package com.prmto.snozeloo.presentation.alarm.detail

sealed interface AlarmDetailViewEvent {
    data object PopBackStack : AlarmDetailViewEvent
    data object NavigateToRingtoneList : AlarmDetailViewEvent
}