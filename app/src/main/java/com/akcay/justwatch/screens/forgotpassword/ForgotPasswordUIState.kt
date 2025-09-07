package com.akcay.justwatch.screens.forgotpassword

data class ForgotPasswordUiState(
    val email: String = "",
    val loading: Boolean = false
)

sealed class ForgotPasswordScreenViewEvent {
    object OnSendResetEmailClicked : ForgotPasswordScreenViewEvent()
}

sealed class ForgotPasswordScreenViewModelEvent {
    object ShowSuccessMessage : ForgotPasswordScreenViewModelEvent()
}
