package com.prmto.snozeloo.domain.repository

import com.prmto.snozeloo.domain.model.Ringtone
import kotlinx.collections.immutable.ImmutableList

interface RingtoneRepository {
    suspend fun getRingtones(): ImmutableList<Ringtone>
}
