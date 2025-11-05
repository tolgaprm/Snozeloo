package com.prmto.snozeloo.presentation.alarm.list

import com.prmto.snozeloo.core.presentation.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import com.prmto.snozeloo.presentation.alarm.list.AlarmListViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(): BaseViewModel<AlarmListViewEvent>() {
    private val _alarmListState = MutableStateFlow<List<AlarmItemUIModel>>(emptyList())
    val alarmListState: StateFlow<List<AlarmItemUIModel>> = _alarmListState.asStateFlow()

    fun onAlarmListAction(action: AlarmListAction) {
        when (action) {
            is AlarmListAction.OnClickAddAlarm -> {
                sendViewEvent(AlarmListViewEvent.NavigateToAlarmDetail(null))
            }

            is AlarmListAction.OnClickAlarmItem -> {
                sendViewEvent(AlarmListViewEvent.NavigateToAlarmDetail(action.alarmId))
            }

            is AlarmListAction.ChangeAlarmEnabled -> {
                // Update database

            }
        }
    }
}