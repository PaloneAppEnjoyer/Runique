@file:OptIn(ExperimentalFoundationApi::class)

package com.palone.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.auth.domain.AuthRepository
import com.palone.auth.domain.UserDataValidator
import com.palone.auth.presentation.R
import com.palone.core.domain.util.DataError
import com.palone.core.domain.util.Result
import com.palone.core.presentation.ui.UiText
import com.palone.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                state.email.text.toString().trim(),
                state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)
            when (result) {
                is Result.Success -> eventChannel.send(LoginEvent.LoginSuccess)
                is Result.Error -> {
                    when (result.error) {
                        DataError.Network.UNAUTHORISED -> {
                            eventChannel.send(LoginEvent.Error(UiText.StringResource(R.string.error_email_password_incorrect)))
                        }

                        else -> {
                            eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                        }
                    }
                }
            }
        }
    }

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(
                    email.toString().trim()
                ) && password.isNotEmpty()
            )

        }.launchIn(viewModelScope)
    }
}