package com.akcay.justwatch.screens.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isPasswordMatches: Boolean = false
)
