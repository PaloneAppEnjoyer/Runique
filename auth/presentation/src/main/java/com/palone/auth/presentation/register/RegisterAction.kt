package com.palone.auth.presentation.register

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityClick : RegisterAction
    data object onLoginClick : RegisterAction
    data object OnRegisterClick : RegisterAction

}