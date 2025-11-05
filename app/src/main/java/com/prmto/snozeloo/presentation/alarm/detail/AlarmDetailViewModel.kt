package com.prmto.snozeloo.presentation.alarm.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.prmto.snozeloo.core.presentation.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.domain.model.defaultAlarmItemUiModel
import com.prmto.snozeloo.domain.usecase.ValidateTimeInputUseCase
import com.prmto.snozeloo.presentation.navigation.AlarmGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlarmDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val validateTimeInputUseCase: ValidateTimeInputUseCase
) : BaseViewModel<AlarmDetailViewEvent>() {

    private val selectedAlarmId = savedStateHandle.toRoute<AlarmGraph.AlarmDetail>().alarmId

    var isSaveButtonEnabled by mutableStateOf(true)
        private set

    private val _alarmDetailState = MutableStateFlow(defaultAlarmItemUiModel())
    val alarmDetailState: StateFlow<AlarmItemUIModel> = _alarmDetailState.asStateFlow()

    init {
        if (selectedAlarmId != null) {
            // Get the alarm from database
        }
    }

    fun onAlarmDetailAction(action: AlarmDetailAction) {
        when (action) {
            is AlarmDetailAction.OnClickClose -> {
                // _alarmDetailState.value = null
                sendViewEvent(AlarmDetailViewEvent.PopBackStack)
            }

            is AlarmDetailAction.OnClickSave -> {
                // Update database
                //  _alarmDetailState.value = null
                sendViewEvent(AlarmDetailViewEvent.PopBackStack)
            }

            is AlarmDetailAction.OnClickAlarmRingtone -> {
                sendViewEvent(AlarmDetailViewEvent.NavigateToRingtoneList)
            }

            is AlarmDetailAction.OnClickedRepeatingDay -> {

                _alarmDetailState.update {
                    val current = alarmDetailState.value

                    val updatedDays: ImmutableSet<DayValue> =
                        if (current.repeatingDays.contains(action.day)) {
                            (current.repeatingDays - action.day).toImmutableSet()
                        } else {
                            (current.repeatingDays + action.day).toImmutableSet()
                        }

                    current.copy(repeatingDays = updatedDays)
                }
            }

            is AlarmDetailAction.OnChangedAlarmHour -> {
                validateTime(
                    value = action.hour,
                    onUpdate = {
                        _alarmDetailState.update {
                            it.copy(
                                timeHour = action.hour
                            )
                        }
                    }
                )
            }

            is AlarmDetailAction.OnChangedAlarmMinute -> {
                validateTime(
                    value = action.minute,
                    onUpdate = {
                        _alarmDetailState.update {
                            it.copy(
                                timeMinute = action.minute
                            )
                        }
                    }
                )
            }

            is AlarmDetailAction.OnChangedAlarmTitle -> {
                _alarmDetailState.update {
                    it.copy(
                        title = action.title
                    )
                }
            }

            is AlarmDetailAction.OnChangedAlarmVolume -> {
                _alarmDetailState.update {
                    it.copy(
                        alarmVolume = action.alarmVolume
                    )
                }
            }

            is AlarmDetailAction.OnChangedAlarmVibrationEnabled -> {
                _alarmDetailState.update {
                    it.copy(
                        isVibrationEnabled = action.isVibrationEnabled
                    )
                }
            }
        }
    }

    private fun validateTime(
        value: String,
        onUpdate: () -> Unit
    ) {
        if (value.isDigitsOnly() && value.length <= 2) {
            onUpdate()
        }

        isSaveButtonEnabled = validateTimeInputUseCase(
            hour = alarmDetailState.value.timeHour,
            minute = alarmDetailState.value.timeMinute
        )
    }
}