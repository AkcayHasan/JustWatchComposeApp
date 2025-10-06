package com.akcay.justwatch.screens.login

import com.akcay.justwatch.internal.util.FormValidationState

sealed interface LoginScreenViewEvent {
    data object OnLoginClicked: LoginScreenViewEvent
}

sealed interface LoginScreenViewModelEvent {
    data object NavigateMoviesScreen: LoginScreenViewModelEvent
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isRememberCheckboxChecked: Boolean = false,
    val loading: Boolean = false,
    val validationState: FormValidationState = FormValidationState()
)
