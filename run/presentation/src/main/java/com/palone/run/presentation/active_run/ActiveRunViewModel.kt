package com.palone.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

class ActiveRunViewModel : ViewModel() {
    var state by mutableStateOf(ActiveRunState())
        private set

    private val eventChannel = Channel<ActiveRunEvent>()

    private val _hasLocationPermission = MutableStateFlow(false)
    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnBackClick -> {}
            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {}
            ActiveRunAction.OnToggleRunClick -> {}
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                _hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(showLocationRationale = action.showLocationRationale)
            }

            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                state =
                    state.copy(showNotificationRationale = action.showNotificationPermissionRationale)
            }

            is ActiveRunAction.DismissRationaleDialog -> {
                state = state.copy(showLocationRationale = false, showNotificationRationale = false)
            }
        }
    }
}