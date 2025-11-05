package com.prmto.snozeloo.domain.usecase

import javax.inject.Inject

class ValidateTimeInputUseCase @Inject constructor() {

    operator fun invoke(hour: String,minute: String): Boolean{
        if (hour.isEmpty() || minute.isEmpty()) {
            return false
        }

        if (hour.length != 2 || minute.length != 2) {
            return false
        }

        val hourInt = hour.toInt()
        val minuteInt = minute.toInt()

        if (hourInt >= 24 || hourInt < 0) {
            return false
        }

        if (minuteInt < 0 || minuteInt >= 60) {
            return false
        }

        return true
    }
}