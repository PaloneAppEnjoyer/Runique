package com.palone.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.run.domain.RunningTracker
import com.palone.run.presentation.active_run.service.ActiveRunService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class ActiveRunViewModel(private val runningTracker: RunningTracker) : ViewModel() {
    var state by mutableStateOf(
        ActiveRunState(
            shouldTrack = ActiveRunService.isServiceActive && runningTracker.isTracking.value,
            hasStartedRunning = ActiveRunService.isServiceActive
        )
    )
        private set

    private val eventChannel = Channel<ActiveRunEvent>()
    private val shouldTrack = snapshotFlow { state.shouldTrack }.stateIn(
        viewModelScope,
        SharingStarted.Lazily, state.shouldTrack
    )
    private val hasLocationPermission = MutableStateFlow(false)

    private val isTracking =
        combine(shouldTrack, hasLocationPermission) { shouldTrack, hasLocationPermission ->
            shouldTrack && hasLocationPermission
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        hasLocationPermission.onEach { hasLocationPermission ->
            if (hasLocationPermission) {
                runningTracker.startObservingLocation()
            } else {
                runningTracker.stopObservingLocation()
            }
        }.launchIn(viewModelScope)

        isTracking.onEach { isTracking ->
            runningTracker.setIsTracking(isTracking)
        }.launchIn(viewModelScope)

        runningTracker.currentLocation.onEach { state = state.copy(currentLocation = it?.location) }
            .launchIn(viewModelScope)

        runningTracker.runData.onEach { state = state.copy(runData = it) }
            .launchIn(viewModelScope)

        runningTracker.elapsedTime.onEach { state = state.copy(elapsedTime = it) }
            .launchIn(viewModelScope)

    }

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnBackClick -> {
                state = state.copy(shouldTrack = true)
            }

            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {
                state = state.copy(shouldTrack = true)
            }

            ActiveRunAction.OnToggleRunClick -> {
                state = state.copy(shouldTrack = !state.shouldTrack, hasStartedRunning = true)
            }

            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                hasLocationPermission.value = action.acceptedLocationPermission
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

    override fun onCleared() {
        super.onCleared()
        if (ActiveRunService.isServiceActive) {
            runningTracker.stopObservingLocation()
        }
    }
}