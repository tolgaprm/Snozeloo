package com.prmto.snozeloo.presentation.alarm.ringtone

import com.prmto.snozeloo.domain.model.Ringtone

sealed interface RingtoneUiEvent {
    data class PopBackStack(
        val selectedRingtoneName: String? = null,
        val selectedRingtoneUri: String? = null
    ) : RingtoneUiEvent

    data class PlayRingtone(val ringtone: Ringtone) : RingtoneUiEvent
}
