package com.prmto.snozeloo.presentation.alarm.ringtone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.prmto.snozeloo.core.util.BaseViewModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import com.prmto.snozeloo.domain.usecase.GetRingtonesUseCase
import com.prmto.snozeloo.presentation.navigation.AlarmGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RingtoneViewModel @Inject constructor(
    private val getRingtonesUseCase: GetRingtonesUseCase,
    savedStateHandle: SavedStateHandle,
    private val alarmRepository: AlarmRepository
) : BaseViewModel<RingtoneUiEvent>() {

    var uiState by mutableStateOf(RingtoneState())
        private set

    private val ringtoneRoute = savedStateHandle.toRoute<AlarmGraph.Ringtone>()

    init {
        viewModelScope.launch {
            loadRingtones()
            ringtoneRoute.ringtoneUri?.let { selectedRingtoneUri ->
                uiState =
                    uiState.copy(selectedRingtone = uiState.ringtones.find { it.uri == selectedRingtoneUri })
            }
        }
    }

    fun onAction(action: RingtoneAction) {
        when (action) {
            is RingtoneAction.OnSelectRingtone -> {
                uiState = uiState.copy(selectedRingtone = action.ringtone)
                sendViewEvent(RingtoneUiEvent.PlayRingtone(action.ringtone))
            }

            RingtoneAction.OnNavigateBack -> {
                sendViewEvent(RingtoneUiEvent.PopBackStack())
            }

            RingtoneAction.OnSave -> {
                if (ringtoneRoute.alarmId != null) {
                    viewModelScope.launch {
                        if (uiState.selectedRingtone?.uri.isNullOrEmpty() || uiState.selectedRingtone?.name.isNullOrEmpty()) return@launch

                        alarmRepository.updateAlarmRingtoneUriAndName(
                            alarmId = ringtoneRoute.alarmId,
                            ringtoneUri = uiState.selectedRingtone?.uri!!,
                            ringtoneName = uiState.selectedRingtone?.name!!
                        )

                        sendViewEvent(
                            RingtoneUiEvent.PopBackStack(
                                selectedRingtoneName = uiState.selectedRingtone?.name,
                                selectedRingtoneUri = uiState.selectedRingtone?.uri
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadRingtones() {
        uiState = uiState.copy(isLoading = true)
        val ringtones = getRingtonesUseCase()
        uiState = uiState.copy(ringtones = ringtones, isLoading = false)
    }
}
