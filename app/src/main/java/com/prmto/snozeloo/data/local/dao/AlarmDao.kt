package com.prmto.snozeloo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.prmto.snozeloo.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Upsert
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Query("SELECT * FROM alarm")
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: String?): AlarmEntity?

    @Query("UPDATE alarm SET isActive = :isActive WHERE id = :alarmId")
    suspend fun updateAlarmEnabled(alarmId: String?, isActive: Boolean)

    @Query("DELETE FROM alarm WHERE id = :alarmId")
    suspend fun deleteAlarmById(alarmId: String?)

    @Query("UPDATE alarm SET alarmRingtoneUri = :ringtoneUri, alarmRingtoneName = :ringtoneName WHERE id = :alarmId")
    suspend fun updateAlarmRingtoneUriAndName(alarmId: String?, ringtoneUri: String?, ringtoneName: String?)
}