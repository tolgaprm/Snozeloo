package com.prmto.snozeloo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.snozeloo.presentation.alarmlist.AlarmListRoute

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
            AlarmListRoute()
        }

        composable<AlarmGraph.AlarmDetail> {

        }

        composable<AlarmGraph.RingtoneList> {

        }
    }
}