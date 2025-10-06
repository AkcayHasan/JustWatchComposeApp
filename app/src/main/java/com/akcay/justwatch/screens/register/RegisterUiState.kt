package com.akcay.justwatch.screens.register

import com.akcay.justwatch.internal.util.FormValidationState

sealed interface RegisterScreenViewEvent {
    data object OnRegisterClicked: RegisterScreenViewEvent
}

sealed interface RegisterScreenViewModelEvent {
    data object NavigateMoviesScreen: RegisterScreenViewModelEvent
}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val surname: String = "",
    val loading: Boolean = false,
    val validationState: FormValidationState = FormValidationState()
)
