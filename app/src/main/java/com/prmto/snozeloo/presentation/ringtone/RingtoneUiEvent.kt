package com.prmto.snozeloo.presentation.ringtone

import com.prmto.snozeloo.domain.model.Ringtone

sealed interface RingtoneUiEvent {
    data object PopBackStack : RingtoneUiEvent

    data class PlayRingtone(val ringtone: Ringtone) : RingtoneUiEvent
}
