package com.prmto.snozeloo.presentation.alarm.list

import com.prmto.snozeloo.core.presentation.util.BaseViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor() : BaseViewModel<AlarmListViewEvent>() {
    private val _alarmListState =
        MutableStateFlow<ImmutableList<AlarmItemUIModel>>(persistentListOf())
    val alarmListState: StateFlow<ImmutableList<AlarmItemUIModel>> = _alarmListState.asStateFlow()

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