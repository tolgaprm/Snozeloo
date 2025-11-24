package com.prmto.snozeloo.domain.usecase

import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
import com.prmto.snozeloo.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(alarmId: String) {
        alarmRepository.deleteAlarmById(alarmId)
        alarmScheduler.cancelAlarm(alarmId)
    }
}