package com.prmto.snozeloo.domain.alarm_manager

import com.prmto.snozeloo.domain.model.AlarmItemUIModel

interface AlarmScheduler {

    fun scheduleAlarm(alarmItemUIModel: AlarmItemUIModel)

    fun cancelAlarm(alarmItemUIModel: AlarmItemUIModel)
}