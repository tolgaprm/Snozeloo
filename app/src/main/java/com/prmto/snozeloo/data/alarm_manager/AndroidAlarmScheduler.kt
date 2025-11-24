package com.prmto.snozeloo.data.alarm_manager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.prmto.snozeloo.core.util.AlarmIntentExt
import com.prmto.snozeloo.core.util.putAlarmItem
import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.toCalendarDay
import java.util.Calendar
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AlarmScheduler {

    override fun scheduleAlarm(alarmItemUIModel: AlarmItemUIModel) {
        val alarmTime = getNextAlarmTime(alarmItemUIModel) ?: return
        val alarmIntentExt = AlarmIntentExt(alarmItemId = alarmItemUIModel.id, isSnooze = false)
        schedule(alarmIntentExt = alarmIntentExt, alarmTime = alarmTime.timeInMillis)
    }

    override fun scheduleAlarmForSnooze(alarmItemId: String, snoozeTimeInMinute: Long) {
        val alarmTime = System.currentTimeMillis() + snoozeTimeInMinute * 60 * 1000
        val alarmIntentExt = AlarmIntentExt(alarmItemId = alarmItemId, isSnooze = true)

        schedule(alarmIntentExt, alarmTime = alarmTime)
    }

    @SuppressLint("MissingPermission")
    private fun schedule(alarmIntentExt: AlarmIntentExt, alarmTime: Long) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putAlarmItem(alarmIntentExt = alarmIntentExt)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmIntentExt.alarmItemId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            pendingIntent
        )
    }

    override fun cancelAlarm(alarmId: String) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun getNextAlarmTime(alarmItem: AlarmItemUIModel): Calendar? {
        val now = Calendar.getInstance()
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarmItem.timeHour.toInt())
            set(Calendar.MINUTE, alarmItem.timeMinute.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val sortedRepeatingDays = alarmItem.repeatingDays.map { it.toCalendarDay() }.sorted()
        val today = now.get(Calendar.DAY_OF_WEEK)

        for (day in sortedRepeatingDays) {
            if (day > today || (day == today && alarmTime.after(now))) {
                alarmTime.set(Calendar.DAY_OF_WEEK, day)
                return alarmTime
            }
        }

        return null
    }

    companion object {
        const val ALARM_ITEM_ID = "alarm_item_id"
        const val IS_SNOOZE = "is_snooze"
    }
}
