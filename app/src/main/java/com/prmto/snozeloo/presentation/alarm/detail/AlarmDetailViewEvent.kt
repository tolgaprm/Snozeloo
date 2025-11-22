package com.prmto.snozeloo.presentation.alarm.detail

sealed interface AlarmDetailViewEvent {
    data object PopBackStack : AlarmDetailViewEvent
    data object NavigateToRingtoneList : AlarmDetailViewEvent

    data class ShowSnackbarMessage(val message: String) : AlarmDetailViewEvent
}