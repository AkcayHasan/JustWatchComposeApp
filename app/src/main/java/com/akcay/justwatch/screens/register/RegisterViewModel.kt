package com.akcay.justwatch.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.internal.ext.isValidEmail
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.firebase.service.AccountService
import com.akcay.justwatch.firebase.service.LogService
import com.akcay.justwatch.navigation.AllScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val logService: LogService,
    private val accountService: AccountService
) : ViewModel() {

    val uiState = mutableStateOf(RegisterUiState())

    private val _loadingStatus = MutableStateFlow(false)
    val loadingStatus: StateFlow<Boolean> = _loadingStatus

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    private val repeatPassword
        get() = uiState.value.repeatPassword

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onRegisterClick(openAndPopUp: (String, String) -> Unit) {

        launchCatching(logService = logService) {
            _loadingStatus.emit(true)
            accountService.register(email = email, password = password)
            openAndPopUp.invoke(MainDestinations.HOME_ROUTE, AllScreens.REGISTER.name)
        }
    }
}