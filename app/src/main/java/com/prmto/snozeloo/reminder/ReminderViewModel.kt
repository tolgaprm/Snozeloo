package com.prmto.snozeloo.reminder

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.snozeloo.core.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : BaseViewModel<ReminderViewEvent>() {

    val alarmItemState = mutableStateOf<AlarmItemUIModel?>(null)

    fun getAlarmItem(alarmItemId: String?) {
        if (alarmItemId == null) return
        viewModelScope.launch {
            alarmItemState.value = alarmRepository.getAlarmById(alarmItemId)
        }
    }

    fun onAction(action: ReminderAction) {
        when (action) {
            is ReminderAction.OnClickTurnOff -> {
                sendViewEvent(ReminderViewEvent.TurnOffAlarm)
            }
            is ReminderAction.OnClickSnooze -> {

            }
        }
    }
}