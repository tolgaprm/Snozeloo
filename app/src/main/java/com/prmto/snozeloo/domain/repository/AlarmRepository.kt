package com.prmto.snozeloo.domain.repository

import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAllAlarms(): Flow<ImmutableList<AlarmItemUIModel>>

    suspend fun getAlarmById(alarmId: String): AlarmItemUIModel?

   suspend fun insertAlarm(alarmItemUIModel: AlarmItemUIModel)
   suspend fun updateAlarmEnabled(alarmId: String, isEnabled: Boolean)

   suspend fun deleteAlarmById(alarmId: String)
}