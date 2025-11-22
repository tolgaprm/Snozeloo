import com.prmto.snozeloo.domain.model.DayValue
import java.util.Calendar
import java.util.concurrent.TimeUnit

object DateUtil {
    fun getNextOccurrenceAlarmTime(
        alarmHourTime: String,
        alarmMinuteTime: String,
        alarmDayValue: Set<DayValue>
    ): String {
        val now = Calendar.getInstance()
        val currentTime = now.time

        var daysToAdd = -1

        // Find the next valid alarm day, checking up to 7 days in the future
        for (i in 0..7) {
            val checkingDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, i) }
            val checkingDayOfWeek = checkingDate.get(Calendar.DAY_OF_WEEK)

            val dayValue = when (checkingDayOfWeek) {
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
                // Create a potential alarm time for this day
                val potentialAlarmTime = Calendar.getInstance().apply {
                    time = checkingDate.time // Start with the correct day
                    set(Calendar.HOUR_OF_DAY, alarmHourTime.toInt())
                    set(Calendar.MINUTE, alarmMinuteTime.toInt())
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                // If this alarm time is in the future, we've found our day
                if (potentialAlarmTime.time > currentTime) {
                    daysToAdd = i
                    break
                }
            }
        }

        // This should not happen with valid inputs, but as a fallback
        if (daysToAdd == -1) return ""

        // Calculate the final alarm time
        val alarmCalendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, daysToAdd)
            set(Calendar.HOUR_OF_DAY, alarmHourTime.toInt())
            set(Calendar.MINUTE, alarmMinuteTime.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Calculate time difference
        val diff = alarmCalendar.timeInMillis - currentTime.time
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60

        return when {
            days > 0 -> "$days d $hours h $minutes min"
            hours > 0 -> "$hours h $minutes min"
            else -> "$minutes min"
        }
    }
}