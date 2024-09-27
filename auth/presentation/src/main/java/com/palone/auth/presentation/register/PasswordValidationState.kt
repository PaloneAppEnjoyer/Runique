package com.palone.auth.presentation.register

data class PasswordValidationState(
    val hasMinimumLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLoweCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
) {
    val isPasswordValid: Boolean
        get() = hasMinimumLength && hasNumber && hasLoweCaseCharacter && hasUpperCaseCharacter
}
