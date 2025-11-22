package com.prmto.snozeloo.data.local.convertor

import androidx.room.TypeConverter
import com.prmto.snozeloo.domain.model.DayValue

class DatabaseConvertor {
    private val separator = ","

    @TypeConverter
    fun convertDaysToDatabaseFormat(days: Set<DayValue>): String {
        return days.joinToString(separator)
    }

    @TypeConverter
    fun convertDaysFromDatabaseFormat(days: String): Set<DayValue> {
        return days.split(separator).filter { it.isNotEmpty() }.map { DayValue.valueOf(it) }.toSet()
    }
}