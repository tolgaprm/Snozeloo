package com.prmto.snozeloo.presentation.alarm.ringtone

import com.prmto.snozeloo.domain.model.Ringtone

sealed interface RingtoneAction {
    data class OnSelectRingtone(val ringtone: Ringtone) : RingtoneAction
    data object OnNavigateBack : RingtoneAction
}
