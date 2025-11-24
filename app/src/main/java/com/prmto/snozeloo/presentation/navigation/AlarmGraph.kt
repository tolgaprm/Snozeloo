package com.prmto.snozeloo.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavigationGraphs {

    @Serializable
    data object Root : AlarmGraph()
}

sealed class AlarmGraph {
    @Serializable
    data object AlarmList : AlarmGraph()
    @Serializable
    data class AlarmDetail(val alarmId: String?) : AlarmGraph()
    @Serializable
    data class Ringtone(val ringtoneUri: String?) : AlarmGraph(){
    }
}