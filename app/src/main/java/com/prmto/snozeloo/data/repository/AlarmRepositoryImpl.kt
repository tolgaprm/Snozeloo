package com.prmto.snozeloo.data.repository

import com.prmto.snozeloo.data.local.convertor.toAlarmEntity
import com.prmto.snozeloo.data.local.convertor.toAlarmItemUIModel
import com.prmto.snozeloo.data.local.dao.AlarmDao
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : AlarmRepository {
    override fun getAllAlarms(): Flow<ImmutableList<AlarmItemUIModel>> {
        return alarmDao.getAllAlarms().map {
            it.map { alarmEntity ->
                alarmEntity.toAlarmItemUIModel()
            }.toImmutableList()
        }
    }

    override suspend fun getAlarmById(alarmId: String): AlarmItemUIModel {
        return alarmDao.getAlarmById(alarmId).toAlarmItemUIModel()
    }

    override suspend fun insertAlarm(alarmItemUIModel: AlarmItemUIModel) {
        val id = alarmItemUIModel.id.ifEmpty {
            UUID.randomUUID().toString()
        }
        alarmDao.insertAlarm(alarmItemUIModel.toAlarmEntity(id))
    }

    override suspend fun updateAlarmEnabled(alarmId: String, isEnabled: Boolean) {
       alarmDao.updateAlarmEnabled(alarmId, isEnabled)
    }

    override suspend fun deleteAlarmById(alarmId: String) {
      alarmDao.deleteAlarmById(alarmId)
    }
}