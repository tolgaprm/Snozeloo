package com.prmto.snozeloo.core.util

import android.content.Intent
import com.prmto.snozeloo.data.alarm_manager.AndroidAlarmScheduler

fun Intent.putAlarmItem(
    alarmIntentExt: AlarmIntentExt,
) {
    putExtra(AndroidAlarmScheduler.ALARM_ITEM_ID, alarmIntentExt.alarmItemId)
    putExtra(AndroidAlarmScheduler.IS_SNOOZE, alarmIntentExt.isSnooze)
}

fun Intent?.getAlarmItemAndIsSnooze(): AlarmIntentExt {
    this ?: return AlarmIntentExt(null, false)
    val alarmItemId = getStringExtra(AndroidAlarmScheduler.ALARM_ITEM_ID)
    val isSnooze = getBooleanExtra(AndroidAlarmScheduler.IS_SNOOZE, false)

    return AlarmIntentExt(
        alarmItemId = alarmItemId,
        isSnooze = isSnooze,
    )
}

data class AlarmIntentExt(
    val alarmItemId: String?,
    val isSnooze: Boolean,
)