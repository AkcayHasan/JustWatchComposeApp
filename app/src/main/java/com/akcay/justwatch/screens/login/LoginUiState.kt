package com.akcay.justwatch.screens.login

sealed interface LoginScreenViewEvent {
    data object OnLoginClicked: LoginScreenViewEvent
    data object OnRegisterClicked: LoginScreenViewEvent
}

sealed interface LoginScreenViewModelEvent {
    data object NavigateMoviesScreen: LoginScreenViewModelEvent
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isRememberCheckboxChecked: Boolean = false,
    val loading: Boolean = false
)
