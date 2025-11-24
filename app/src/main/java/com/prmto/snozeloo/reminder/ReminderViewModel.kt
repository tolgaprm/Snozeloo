package com.prmto.snozeloo.reminder

import DateUtil.ALARM_SNOOZE_TIME_IN_MINUTE
import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.prmto.snozeloo.core.util.AlarmIntentExt
import com.prmto.snozeloo.core.util.BaseViewModel
import com.prmto.snozeloo.domain.alarm_manager.AlarmScheduler
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) : BaseViewModel<ReminderViewEvent>() {

    val alarmItemState = mutableStateOf<AlarmItemUIModel?>(null)

    fun getAlarmItem(alarmIntentExt: AlarmIntentExt) {
        if (alarmIntentExt.alarmItemId == null) return
        viewModelScope.launch {
            val alarmItem = alarmRepository.getAlarmById(alarmIntentExt.alarmItemId)

            if (alarmIntentExt.isSnooze && alarmItem != null) {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                }
                val newHour = calendar.get(Calendar.HOUR_OF_DAY)
                val newMinute = calendar.get(Calendar.MINUTE)

                alarmItemState.value = alarmItem.copy(
                    timeHour = String.format(Locale.ROOT,"%02d", newHour),
                    timeMinute = String.format(Locale.ROOT,"%02d", newMinute)
                )
            } else {
                alarmItemState.value = alarmItem
            }
        }
    }

    fun onAction(action: ReminderAction) {
        when (action) {
            is ReminderAction.OnClickTurnOff -> {
                alarmScheduler.scheduleAlarm(alarmItemState.value ?: return)
                sendViewEvent(ReminderViewEvent.TurnOffAlarm)
            }

            is ReminderAction.OnClickSnooze -> {
                alarmScheduler.scheduleAlarmForSnooze(
                    alarmItemState.value?.id ?: "",
                    ALARM_SNOOZE_TIME_IN_MINUTE
                )
                sendViewEvent(ReminderViewEvent.TurnOffAlarm)
            }
        }
    }
}