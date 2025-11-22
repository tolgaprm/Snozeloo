package com.prmto.snozeloo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.snozeloo.domain.model.DayValue

@Entity("alarm")
data class AlarmEntity(
    @PrimaryKey val id: String,
    val title: String,
    val timeHour: String,
    val timeMinute: String,
    val isActive: Boolean,
    val repeatingDays:Set<DayValue>,
    val isVibrationEnabled: Boolean,
    val alarmVolume: Float,
    val alarmRingtone: String
)