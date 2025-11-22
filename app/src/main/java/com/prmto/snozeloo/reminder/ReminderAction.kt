package com.prmto.snozeloo.reminder

sealed interface ReminderAction {
    data object OnClickTurnOff : ReminderAction
    data object OnClickSnooze : ReminderAction
}