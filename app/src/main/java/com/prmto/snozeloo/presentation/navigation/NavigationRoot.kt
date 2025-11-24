package com.prmto.snozeloo.presentation.navigation

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prmto.snozeloo.core.util.ObserveAsEvents
import com.prmto.snozeloo.presentation.alarm.detail.AlarmDetailAction
import com.prmto.snozeloo.presentation.alarm.detail.AlarmDetailScreen
import com.prmto.snozeloo.presentation.alarm.detail.AlarmDetailViewEvent
import com.prmto.snozeloo.presentation.alarm.detail.AlarmDetailViewModel
import com.prmto.snozeloo.presentation.alarm.list.AlarmListScreen
import com.prmto.snozeloo.presentation.alarm.list.AlarmListViewEvent
import com.prmto.snozeloo.presentation.alarm.list.AlarmListViewModel
import com.prmto.snozeloo.presentation.alarm.ringtone.RingtonePlayManager
import com.prmto.snozeloo.presentation.alarm.ringtone.RingtoneScreen
import com.prmto.snozeloo.presentation.alarm.ringtone.RingtoneUiEvent
import com.prmto.snozeloo.presentation.alarm.ringtone.RingtoneViewModel
import com.prmto.snozeloo.presentation.alarm.ringtone.playmanager.RingtonePlayState
import com.prmto.snozeloo.presentation.alarm.ringtone.playmanager.rememberRingtonePlayManager

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
            val viewModel = hiltViewModel<AlarmListViewModel>()
            val alarms by viewModel.alarmListState.collectAsStateWithLifecycle()
            AlarmListScreen(
                alarms = alarms,
                onAction = viewModel::onAlarmListAction
            )

            ObserveAsEvents(
                flow = viewModel.viewEvent
            ) { event ->
                when (event) {
                    is AlarmListViewEvent.NavigateToAlarmDetail -> {
                        navController.navigateToAlarmDetail(alarmId = event.alarmId)
                    }
                }
            }
        }

        composable<AlarmGraph.AlarmDetail> {
            val viewModel = hiltViewModel<AlarmDetailViewModel>()
            val alarmDetail by viewModel.alarmDetailState.collectAsStateWithLifecycle()
            val ringtoneName = navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                SELECTED_RINGTONE_NAME
            )
            val ringtoneUri = navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                SELECTED_RINGTONE_URI
            )

            viewModel.onAlarmDetailAction(
                AlarmDetailAction.OnSelectAlarmRingtone(
                    ringtoneName = ringtoneName,
                    ringtoneUri = ringtoneUri
                )
            )

            val snackbarHostState = remember { SnackbarHostState() }

            AlarmDetailScreen(
                alarmDetail = alarmDetail,
                isSaveButtonEnable = viewModel.isSaveButtonEnabled,
                onAction = viewModel::onAlarmDetailAction
            )

            SnackbarHost(hostState = snackbarHostState)

            ObserveAsEvents(flow = viewModel.viewEvent) { event ->
                when (event) {
                    is AlarmDetailViewEvent.PopBackStack -> {
                        navController.popBackStack()
                    }

                    is AlarmDetailViewEvent.NavigateToRingtoneList -> {
                        navController.navigate(
                            AlarmGraph.Ringtone(
                                alarmId = event.alarmId,
                                ringtoneUri = event.ringtoneUri
                            )
                        )
                    }

                    is AlarmDetailViewEvent.ShowSnackbarMessage -> {
                        snackbarHostState.showSnackbar(message = event.message)
                    }
                }
            }
        }

        composable<AlarmGraph.Ringtone> {

            val viewModel = hiltViewModel<RingtoneViewModel>()
            val ringtonePlayState: RingtonePlayState = rememberRingtonePlayManager()
            RingtoneScreen(
                state = viewModel.uiState,
                onAction = viewModel::onAction
            )

            ObserveAsEvents(flow = viewModel.viewEvent) { event ->
                when (event) {
                    is RingtoneUiEvent.PopBackStack -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            SELECTED_RINGTONE_NAME,
                            event.selectedRingtoneName
                        )
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            SELECTED_RINGTONE_URI,
                            event.selectedRingtoneUri
                        )
                        navController.popBackStack()
                    }

                    is RingtoneUiEvent.PlayRingtone -> {
                        ringtonePlayState.updateRingtoneUri(
                            ringtoneUri = event.ringtone.uri
                        )
                    }
                }
            }

            RingtonePlayManager(
                ringtonePlayState = ringtonePlayState
            )
        }
    }
}

const val SELECTED_RINGTONE_NAME = "selectedRingtoneName"
const val SELECTED_RINGTONE_URI = "selectedRingtoneUri"

private fun NavController.navigateToAlarmDetail(alarmId: String?) {
    navigate(AlarmGraph.AlarmDetail(alarmId))
}