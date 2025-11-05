package com.prmto.snozeloo.presentation.alarm.detail

import com.prmto.snozeloo.core.presentation.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlarmDetailViewModel @Inject constructor() : BaseViewModel<AlarmDetailViewEvent>() {

    private val _alarmDetailState = MutableStateFlow<AlarmItemUIModel?>(null)
    val alarmDetailState: StateFlow<AlarmItemUIModel?> = _alarmDetailState.asStateFlow()

    fun onAlarmDetailAction(action: AlarmDetailAction) {
        when (action) {
            is AlarmDetailAction.OnClickClose -> {
                _alarmDetailState.value = null
                sendViewEvent(AlarmDetailViewEvent.PopBackStack)
            }

            is AlarmDetailAction.OnClickSave -> {
                // Update database
                _alarmDetailState.value = null
                sendViewEvent(AlarmDetailViewEvent.PopBackStack)
            }

            is AlarmDetailAction.OnClickAlarmRingtone -> {
                sendViewEvent(AlarmDetailViewEvent.NavigateToRingtoneList)
            }

            is AlarmDetailAction.OnClickedRepeatingDay -> {
                _alarmDetailState.update {
                    it?.copy(
                        repeatingDays = if (it.repeatingDays.contains(action.day)){
                            it.repeatingDays - action.day
                        } else {
                            it.repeatingDays + action.day
                        }
                    )
                }
            }

            is AlarmDetailAction.OnChangedAlarmHour -> {

            }

            is AlarmDetailAction.OnChangedAlarmMinute -> {

            }

            is AlarmDetailAction.OnChangedAlarmTitle -> {
                _alarmDetailState.update {
                    it?.copy(
                        title = action.title
                    )
                }
            }

            is AlarmDetailAction.OnChangedAlarmVolume -> {
                _alarmDetailState.update {
                    it?.copy(
                        alarmVolume = action.alarmVolume
                    )
                }
            }

            is AlarmDetailAction.OnChangedAlarmVibrationEnabled -> {
                _alarmDetailState.update {
                    it?.copy(
                        isVibrationEnabled = !action.isVibrationEnabled
                    )
                }
            }
        }
    }
}