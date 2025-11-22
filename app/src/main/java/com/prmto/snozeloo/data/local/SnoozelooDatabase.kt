package com.prmto.snozeloo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prmto.snozeloo.data.local.convertor.DatabaseConvertor
import com.prmto.snozeloo.data.local.dao.AlarmDao
import com.prmto.snozeloo.data.local.entity.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    version = 1
)
@TypeConverters(DatabaseConvertor::class)
abstract class SnoozelooDatabase : RoomDatabase() {

    abstract val alarmDao: AlarmDao
}