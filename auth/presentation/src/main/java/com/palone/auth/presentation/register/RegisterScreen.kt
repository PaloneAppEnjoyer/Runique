@file:OptIn(ExperimentalFoundationApi::class)


package com.palone.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.auth.domain.PasswordValidationState
import com.palone.auth.domain.UserDataValidator
import com.palone.auth.presentation.R
import com.palone.core.presentation.designsystem.CheckIcon
import com.palone.core.presentation.designsystem.CrossIcon
import com.palone.core.presentation.designsystem.EmailIcon
import com.palone.core.presentation.designsystem.Poppins
import com.palone.core.presentation.designsystem.RuniqueDarkRed
import com.palone.core.presentation.designsystem.RuniqueGray
import com.palone.core.presentation.designsystem.RuniqueGreen
import com.palone.core.presentation.designsystem.RuniqueTheme
import com.palone.core.presentation.designsystem.components.GradientBackground
import com.palone.core.presentation.designsystem.components.RuniqueActionButton
import com.palone.core.presentation.designsystem.components.RuniquePasswordTextField
import com.palone.core.presentation.designsystem.components.RuniqueTextField
import com.palone.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(context, R.string.registration_successful, Toast.LENGTH_LONG).show()
                onSuccessfulRegistration()
            }
        }

    }
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.onLoginClick -> onSignInClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(fontFamily = Poppins, color = RuniqueGray)) {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary, fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(id = R.string.login))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.onLoginClick)
                    }
                })
            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                modifier = Modifier.fillMaxWidth(),
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) {
                    CheckIcon
                } else {
                    null
                },
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                additionalInfo = stringResource(id = R.string.must_be_valid_email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(id = R.string.your_password),
                title = stringResource(id = R.string.password),
                onTogglePasswordVisibility = { onAction(RegisterAction.OnTogglePasswordVisibilityClick) },
                isPasswordVisible = state.isPasswordVisible,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordRequirement(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinimumLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.at_least_one_number
                ),
                isValid = state.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.contains_lowercase_character,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasLowerCaseCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.contains_uppercase_character,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasUpperCaseCharacter
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueActionButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                onClick = { onAction(RegisterAction.OnRegisterClick) }

            )
        }
    }
}

@Composable
fun PasswordRequirement(modifier: Modifier = Modifier, text: String, isValid: Boolean) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) RuniqueGreen else RuniqueDarkRed
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RuniqueTheme {
        RegisterScreen(
            state = RegisterState(passwordValidationState = PasswordValidationState(hasNumber = true)),
            onAction = {}
        )
    }
}