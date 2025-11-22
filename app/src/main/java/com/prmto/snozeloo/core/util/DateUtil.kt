import com.prmto.snozeloo.domain.model.DayValue
import java.util.Calendar
import java.util.concurrent.TimeUnit

object DateUtil {
    fun getNextOccurrenceAlarmTime(
        alarmHourTime: String,
        alarmMinuteTime: String,
        alarmDayValue: Set<DayValue>
    ): String {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        val currentTime = calendar.time

        // Convert alarm time to Calendar
        val alarmCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarmHourTime.toInt())
            set(Calendar.MINUTE, alarmMinuteTime.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // If alarm time is in the past today, move to next day
        if (alarmCalendar.time <= currentTime) {
            alarmCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Find the next day with alarm set
        var daysToAdd = 0
        var foundDay = false

        // Check next 7 days to find the next alarm day
        repeat(7) { i ->
            if (!foundDay) {
                val checkDay = (currentDay + i - 1) % 7 + 1 // Convert to 1-7 range (Sunday=1, Monday=2, ...)
                val dayValue = when (checkDay) {
                    Calendar.MONDAY -> DayValue.MONDAY
                    Calendar.TUESDAY -> DayValue.TUESDAY
                    Calendar.WEDNESDAY -> DayValue.WEDNESDAY
                    Calendar.THURSDAY -> DayValue.THURSDAY
                    Calendar.FRIDAY -> DayValue.FRIDAY
                    Calendar.SATURDAY -> DayValue.SATURDAY
                    Calendar.SUNDAY -> DayValue.SUNDAY
                    else -> null
                }

                if (dayValue in alarmDayValue) {
                    daysToAdd = i
                    foundDay = true
                }
            }
        }

        // If no day found in the next 7 days, find the first day in the set
        if (!foundDay && alarmDayValue.isNotEmpty()) {
            val firstDay = alarmDayValue.minByOrNull {
                when (it) {
                    DayValue.MONDAY -> 1
                    DayValue.TUESDAY -> 2
                    DayValue.WEDNESDAY -> 3
                    DayValue.THURSDAY -> 4
                    DayValue.FRIDAY -> 5
                    DayValue.SATURDAY -> 6
                    DayValue.SUNDAY -> 7
                }
            }

            val firstDayValue = when (firstDay) {
                DayValue.MONDAY -> Calendar.MONDAY
                DayValue.TUESDAY -> Calendar.TUESDAY
                DayValue.WEDNESDAY -> Calendar.WEDNESDAY
                DayValue.THURSDAY -> Calendar.THURSDAY
                DayValue.FRIDAY -> Calendar.FRIDAY
                DayValue.SATURDAY -> Calendar.SATURDAY
                DayValue.SUNDAY -> Calendar.SUNDAY
                else -> currentDay
            }

            daysToAdd = (7 - (currentDay - firstDayValue)) % 7
            if (daysToAdd == 0) daysToAdd = 7 // If same day, show next week
        }

        // Set the alarm time for the next occurrence
        alarmCalendar.set(Calendar.HOUR_OF_DAY, alarmHourTime.toInt())
        alarmCalendar.set(Calendar.MINUTE, alarmMinuteTime.toInt())
        alarmCalendar.add(Calendar.DAY_OF_YEAR, daysToAdd)

        // Calculate time difference
        val diff = alarmCalendar.timeInMillis - currentTime.time
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60

        return if (hours > 0) {
            "$hours h ${minutes} min"
        } else {
            "${minutes} min"
        }
    }
}