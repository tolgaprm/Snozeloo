package com.prmto.snozeloo.domain.usecase

import com.prmto.snozeloo.domain.model.Ringtone
import com.prmto.snozeloo.domain.repository.RingtoneRepository
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

class GetRingtonesUseCase @Inject constructor(
    private val ringtoneRepository: RingtoneRepository
) {
    suspend operator fun invoke(): ImmutableList<Ringtone> {
        return ringtoneRepository.getRingtones()
    }
}
