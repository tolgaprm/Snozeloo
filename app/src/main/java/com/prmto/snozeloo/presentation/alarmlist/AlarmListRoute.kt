package com.prmto.snozeloo.presentation.alarmlist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AlarmListRoute() {
    val viewModel = hiltViewModel<AlarmListViewModel>()
    AlarmListScreen()
}

@Composable
private fun AlarmListScreen() {

}