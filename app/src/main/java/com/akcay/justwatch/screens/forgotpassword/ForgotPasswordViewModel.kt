package com.akcay.justwatch.screens.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _channel = Channel<ForgotPasswordScreenViewModelEvent>()
    val channel = _channel.receiveAsFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun sendEvent(event: ForgotPasswordScreenViewEvent) {
        when (event) {
            ForgotPasswordScreenViewEvent.OnSendResetEmailClicked -> {
                sendResetEmail()
            }
        }
    }

    private fun sendResetEmail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)

            // TODO: Implement actual password reset logic
            // For now, just simulate API call
            kotlinx.coroutines.delay(500)

            _uiState.update {
                it.copy(loading = false)
            }
            _channel.send(ForgotPasswordScreenViewModelEvent.ShowSuccessMessage)
        }
    }
}
