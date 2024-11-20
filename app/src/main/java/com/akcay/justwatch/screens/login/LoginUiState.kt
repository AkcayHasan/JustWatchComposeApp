package com.akcay.justwatch.screens.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isRememberCheckboxChecked: Boolean = false,
    val loading: Boolean = false
)
