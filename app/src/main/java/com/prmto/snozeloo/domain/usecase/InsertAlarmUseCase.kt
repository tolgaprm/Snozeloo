package com.prmto.snozeloo.domain.usecase

import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import javax.inject.Inject

class InsertAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(alarmItemUIModel: AlarmItemUIModel): Result<String> {

        if (alarmItemUIModel.repeatingDays.isEmpty()){
            return Result.failure(Exception("Alarm repeating days cannot be empty"))
        }

        alarmScheduler.scheduleAlarm(alarmItemUIModel)
        alarmRepository.insertAlarm(alarmItemUIModel)
        return Result.success("Alarm inserted successfully")
    }
}