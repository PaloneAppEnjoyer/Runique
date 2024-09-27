package com.palone.auth.presentation.intro

sealed interface IntroAction {
    data object onSignInClick : IntroAction
    data object onSignUpClick : IntroAction
}