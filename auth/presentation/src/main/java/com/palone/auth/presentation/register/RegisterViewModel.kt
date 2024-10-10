@file:OptIn(ExperimentalFoundationApi::class)


package com.palone.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(private val userDataValidator: UserDataValidator) : ViewModel() {
    var state by mutableStateOf(RegisterState())
    fun onAction(action: RegisterAction) {

    }

    init {
        state.email.textAsFlow().onEach { email ->
            val isValidEmail = userDataValidator.isValidEmail(email.toString())
            state = state.copy(
                isEmailValid = isValidEmail,
                canRegister = isValidEmail && state.passwordValidationState.isPasswordValid && !state.isRegistering
            )

        }.launchIn(viewModelScope)

        state.password.textAsFlow().onEach { password ->
            val passwordValidationState =
                userDataValidator.validatePassword(password = password.toString())
            state =
                state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && state.passwordValidationState.isPasswordValid && !state.isRegistering
                )
        }.launchIn(viewModelScope)
    }

}