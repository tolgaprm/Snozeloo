package com.prmto.snozeloo.presentation.alarm.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.prmto.snozeloo.core.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.model.DayValue
import com.prmto.snozeloo.domain.model.defaultAlarmItemUiModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import com.prmto.snozeloo.domain.usecase.DeleteAlarmUseCase
import com.prmto.snozeloo.domain.usecase.InsertAlarmUseCase
import com.prmto.snozeloo.domain.usecase.ValidateTimeInputUseCase
import com.prmto.snozeloo.presentation.navigation.AlarmGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val validateTimeInputUseCase: ValidateTimeInputUseCase,
    private val alarmRepository: AlarmRepository,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : BaseViewModel<AlarmDetailViewEvent>() {

    private val selectedAlarmId = savedStateHandle.toRoute<AlarmGraph.AlarmDetail>().alarmId

    var isSaveButtonEnabled by mutableStateOf(true)
        private set

    private val _alarmDetailState = MutableStateFlow(defaultAlarmItemUiModel())
    val alarmDetailState: StateFlow<AlarmItemUIModel> = _alarmDetailState.asStateFlow()

    init {
        if (selectedAlarmId != null) {
            viewModelScope.launch {
                alarmRepository.getAlarmById(selectedAlarmId)?.let {
                    _alarmDetailState.value = it
                }
            }
        }
    }

    fun onAlarmDetailAction(action: AlarmDetailAction) {
        when (action) {
            is AlarmDetailAction.OnClickClose -> {
                sendViewEvent(AlarmDetailViewEvent.PopBackStack)
            }

            is AlarmDetailAction.OnClickSave -> {
                viewModelScope.launch {
                    val result = insertAlarmUseCase(alarmDetailState.value)
                    result.onFailure {
                        sendViewEvent(AlarmDetailViewEvent.ShowSnackbarMessage(it.message.toString()))
                    }

                    result.onSuccess {
                        sendViewEvent(AlarmDetailViewEvent.PopBackStack)
                    }
                }
            }

            is AlarmDetailAction.OnClickAlarmRingtone -> {
                sendViewEvent(
                    AlarmDetailViewEvent.NavigateToRingtoneList(
                        alarmId = selectedAlarmId,
                        ringtoneUri = alarmDetailState.value.alarmRingtoneUri
                    )
                )
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

            is AlarmDetailAction.OnClickDelete -> {
                viewModelScope.launch {
                    selectedAlarmId?.let {
                        deleteAlarmUseCase(selectedAlarmId)
                        sendViewEvent(AlarmDetailViewEvent.PopBackStack)
                    }
                }
            }

            is AlarmDetailAction.OnSelectAlarmRingtone -> {
                if (action.ringtoneName != null && action.ringtoneUri != null) {
                    viewModelScope.launch {
                        _alarmDetailState.update {
                            it.copy(
                                alarmRingtoneName = action.ringtoneName,
                                alarmRingtoneUri = action.ringtoneUri
                            )
                        }
                    }
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