package com.prmto.snozeloo.presentation.alarm

import androidx.lifecycle.ViewModel
import com.prmto.snozeloo.domain.model.AlarmItemUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmSharedViewModel @Inject constructor() : ViewModel() {
    private val _alarmListState = MutableStateFlow<List<AlarmItemUIModel>>(emptyList())
    val alarmListState: StateFlow<List<AlarmItemUIModel>> = _alarmListState.asStateFlow()

}