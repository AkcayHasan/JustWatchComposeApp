package com.akcay.justwatch.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.ext.launchCatching
import com.akcay.justwatch.firebase.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logService: LogService
): ViewModel() {
    val uiState = mutableStateOf(LoginUiState())

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLoginClick(openAndPopUp: (String, String) -> Unit) {




        launchCatching(logService) {

        }
    }
}