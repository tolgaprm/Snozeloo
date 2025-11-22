package com.prmto.snozeloo.presentation.alarm.list

import androidx.lifecycle.viewModelScope
import com.prmto.snozeloo.core.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.domain.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : BaseViewModel<AlarmListViewEvent>() {
    private val _alarmListState =
        MutableStateFlow<ImmutableList<AlarmItemUIModel>>(persistentListOf())
    val alarmListState: StateFlow<ImmutableList<AlarmItemUIModel>> = alarmRepository.getAllAlarms()
        .stateIn(viewModelScope, SharingStarted.Eagerly, _alarmListState.value)

    fun onAlarmListAction(action: AlarmListAction) {
        when (action) {
            is AlarmListAction.OnClickAddAlarm -> {
                sendViewEvent(AlarmListViewEvent.NavigateToAlarmDetail(null))
            }

            is AlarmListAction.OnClickAlarmItem -> {
                sendViewEvent(AlarmListViewEvent.NavigateToAlarmDetail(action.alarmId))
            }

            is AlarmListAction.ChangeAlarmEnabled -> {
                viewModelScope.launch {
                    alarmRepository.updateAlarmEnabled(action.alarmId, action.isEnabled)
                }
            }
        }
    }
}