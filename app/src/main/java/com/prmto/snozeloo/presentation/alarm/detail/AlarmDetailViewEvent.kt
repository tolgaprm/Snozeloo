package com.prmto.snozeloo.presentation.alarm.detail

sealed interface AlarmDetailViewEvent {
    data object PopBackStack : AlarmDetailViewEvent
    data class NavigateToRingtoneList(
        val alarmId: String?,
        val ringtoneUri: String?
    ) :
        AlarmDetailViewEvent

    data class ShowSnackbarMessage(val message: String) : AlarmDetailViewEvent
}