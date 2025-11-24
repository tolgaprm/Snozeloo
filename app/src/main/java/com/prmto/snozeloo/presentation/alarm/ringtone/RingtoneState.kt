package com.prmto.snozeloo.presentation.alarm.ringtone

import com.prmto.snozeloo.domain.model.Ringtone
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RingtoneState(
    val ringtones: ImmutableList<Ringtone> = persistentListOf(),
    val selectedRingtoneUri: String? = null,
    val isLoading: Boolean = false
)
