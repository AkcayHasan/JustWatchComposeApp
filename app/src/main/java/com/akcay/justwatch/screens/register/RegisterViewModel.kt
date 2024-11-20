package com.akcay.justwatch.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.usecase.RegisterUseCase
import com.akcay.justwatch.domain.usecase.SaveUserInfoUseCase
import com.akcay.justwatch.internal.navigation.AllScreens
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val logRepository: LogRepository,
    private val registerUseCase: RegisterUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase
) : ViewModel() {

    val uiState = mutableStateOf(RegisterUiState())

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

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

        launchCatching(logRepository = logRepository) {

            when(val result = registerUseCase(email, password)) {
                is NetworkResult.Success -> {

                    saveUserInfoUseCase.invoke(result.data.id!!, "Hasan", "Akçay")
                    openAndPopUp.invoke(MainDestinations.HOME_ROUTE, AllScreens.REGISTER.name)
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {


                }
            }
        }
    }
}