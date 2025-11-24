package com.prmto.snozeloo.presentation.ringtone

sealed interface RingtoneUiEvent {
    data object PopBackStack : RingtoneUiEvent

}
