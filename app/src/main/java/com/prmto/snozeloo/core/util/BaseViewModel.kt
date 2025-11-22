package com.prmto.snozeloo.core.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewEvent> : ViewModel() {
    private val _viewEvent = Channel<ViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()


    fun sendViewEvent(event: ViewEvent) {
        viewModelScope.launch {
            _viewEvent.send(event)
        }
    }
}