package com.akcay.justwatch.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.firebase.service.AccountService
import com.akcay.justwatch.firebase.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logService: LogService,
    private val accountService: AccountService
): ViewModel() {
    val uiState = mutableStateOf(LoginUiState())

    private val _loadingStatus = MutableStateFlow(false)
    val loadingStatus: StateFlow<Boolean> = _loadingStatus

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

    fun createAnonymousUser(openAndPopUp: (String, String) -> Unit) {

        launchCatching(logService) {
            _loadingStatus.emit(true)
            accountService.createAnonymousUser()
            openAndPopUp.invoke(
                MainDestinations.HOME_ROUTE,
                MainDestinations.LOGIN_ROUTE
            )
        }
    }
}