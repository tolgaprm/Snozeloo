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
    data object AlarmDetail : AlarmGraph()
    @Serializable
    data object RingtoneList : AlarmGraph()
}