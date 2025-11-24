package com.prmto.snozeloo.presentation.ringtone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.prmto.snozeloo.core.util.BaseViewModel
import com.prmto.snozeloo.domain.usecase.GetRingtonesUseCase
import com.prmto.snozeloo.presentation.navigation.AlarmGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RingtoneViewModel @Inject constructor(
    private val getRingtonesUseCase: GetRingtonesUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<RingtoneUiEvent>() {

    var uiState by mutableStateOf(RingtoneState())
        private set

    init {
        loadRingtones()
        savedStateHandle.toRoute<AlarmGraph.Ringtone>().ringtoneUri?.let {
            uiState = uiState.copy(selectedRingtoneUri = it)
        }
    }

    fun onAction(action: RingtoneAction) {
        when (action) {
            is RingtoneAction.OnSelectRingtone -> {
                uiState = uiState.copy(selectedRingtoneUri = action.ringtone.uri)
            }

            RingtoneAction.OnNavigateBack -> {
                sendViewEvent(RingtoneUiEvent.PopBackStack)
            }
        }
    }

    private fun loadRingtones() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val ringtones = getRingtonesUseCase()
            uiState = uiState.copy(ringtones = ringtones, isLoading = false)
        }
    }
}
