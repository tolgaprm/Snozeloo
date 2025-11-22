package com.prmto.snozeloo.data.alarm_manager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.toCalendarDay
import java.util.Calendar
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AlarmScheduler {

    @SuppressLint("ScheduleExactAlarm")
    override fun scheduleAlarm(alarmItemUIModel: AlarmItemUIModel) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_ITEM_ID, alarmItemUIModel.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmItemUIModel.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmTime = getNextAlarmTime(alarmItemUIModel)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime.timeInMillis,
            pendingIntent
        )
    }

    override fun cancelAlarm(alarmItemUIModel: AlarmItemUIModel) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmItemUIModel.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun getNextAlarmTime(alarmItem: AlarmItemUIModel): Calendar {
        val now = Calendar.getInstance()
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarmItem.timeHour.toInt())
            set(Calendar.MINUTE, alarmItem.timeMinute.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (alarmItem.repeatingDays.isEmpty()) {
            if (alarmTime.before(now)) {
                alarmTime.add(Calendar.DAY_OF_MONTH, 1)
            }
            return alarmTime
        }

        val sortedRepeatingDays = alarmItem.repeatingDays.map { it.toCalendarDay() }.sorted()
        val today = now.get(Calendar.DAY_OF_WEEK)

        for (day in sortedRepeatingDays) {
            if (day > today || (day == today && alarmTime.after(now))) {
                alarmTime.set(Calendar.DAY_OF_WEEK, day)
                return alarmTime
            }
        }

        return alarmTime
    }

    companion object {
        const val ALARM_ITEM_ID = "alarm_item_id"
    }
}
