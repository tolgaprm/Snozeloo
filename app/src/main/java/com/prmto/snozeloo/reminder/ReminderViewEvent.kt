package com.prmto.snozeloo.reminder

sealed interface ReminderViewEvent {
    data object TurnOffAlarm : ReminderViewEvent

}