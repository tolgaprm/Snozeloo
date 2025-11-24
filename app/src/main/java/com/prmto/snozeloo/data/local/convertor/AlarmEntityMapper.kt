package com.prmto.snozeloo.data.local.convertor

import com.prmto.snozeloo.data.local.entity.AlarmEntity
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import kotlinx.collections.immutable.toImmutableSet

fun AlarmEntity.toAlarmItemUIModel(): AlarmItemUIModel {
    return AlarmItemUIModel(
        id = id ,
        title = title,
        timeHour = timeHour,
        timeMinute = timeMinute,
        isActive = isActive,
        repeatingDays = repeatingDays.toImmutableSet(),
        isVibrationEnabled = isVibrationEnabled,
        alarmVolume = alarmVolume,
        alarmRingtoneUri = alarmRingtone,
        nextOccurrenceAlarmTime = DateUtil.getNextOccurrenceAlarmTime(
            alarmHourTime = timeHour,
            alarmMinuteTime = timeMinute,
            alarmDayValue = repeatingDays.toImmutableSet()
        ),
    )
}

fun AlarmItemUIModel.toAlarmEntity(id: String): AlarmEntity {
    return AlarmEntity(
        id = id,
        title = title,
        timeHour = timeHour,
        timeMinute = timeMinute,
        isActive = isActive,
        repeatingDays = repeatingDays,
        isVibrationEnabled = isVibrationEnabled,
        alarmVolume = alarmVolume,
        alarmRingtone = alarmRingtoneUri,
    )
}