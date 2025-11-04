package com.prmto.snozeloo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.snozeloo.presentation.alarm.list.AlarmListScreen
import com.prmto.snozeloo.presentation.alarm.AlarmSharedViewModel

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NavigationGraphs.Root) {
        alarmGraph(navController)
    }
}

private fun NavGraphBuilder.alarmGraph(navController: NavHostController) {
    navigation<NavigationGraphs.Root>(
        startDestination = AlarmGraph.AlarmList
    ) {
        composable<AlarmGraph.AlarmList> {
            val viewModel = it.sharedViewModel<AlarmSharedViewModel>(navController)
            val alarms by viewModel.alarmListState.collectAsStateWithLifecycle()
            AlarmListScreen(alarms = alarms)
        }

        composable<AlarmGraph.AlarmDetail> {
            val viewModel = it.sharedViewModel<AlarmSharedViewModel>(navController)
        }

        composable<AlarmGraph.RingtoneList> {

        }
    }
}