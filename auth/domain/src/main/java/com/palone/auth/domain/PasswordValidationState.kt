package com.palone.auth.domain

data class PasswordValidationState(
    val hasMinimumLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
) {
    val isPasswordValid: Boolean
        get() = hasMinimumLength && hasNumber && hasLowerCaseCharacter && hasUpperCaseCharacter
}
