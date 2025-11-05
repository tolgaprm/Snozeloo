package com.prmto.snozeloo.presentation.alarm

import com.prmto.snozeloo.core.presentation.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.presentation.alarm.list.AlarmListAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmSharedViewModel @Inject constructor() : BaseViewModel<AlarmViewEvent>() {
    private val _alarmListState = MutableStateFlow<List<AlarmItemUIModel>>(emptyList())
    val alarmListState: StateFlow<List<AlarmItemUIModel>> = _alarmListState.asStateFlow()

    private val _alarmDetailState = MutableStateFlow<AlarmItemUIModel?>(null)
    val alarmDetailState: StateFlow<AlarmItemUIModel?> = _alarmDetailState.asStateFlow()

    fun onAlarmListAction(action: AlarmListAction) {
        when (action) {
            is AlarmListAction.OnClickAddAlarm -> {
               sendViewEvent(AlarmViewEvent.NavigateToAlarmDetail(null))
            }

            is AlarmListAction.OnClickAlarmItem -> {
                sendViewEvent(AlarmViewEvent.NavigateToAlarmDetail(action.alarmId))
            }

            is AlarmListAction.ChangeAlarmEnabled -> {
                // Update database

            }
        }
    }
}