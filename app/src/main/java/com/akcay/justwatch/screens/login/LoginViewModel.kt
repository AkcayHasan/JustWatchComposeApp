package com.akcay.justwatch.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.domain.usecase.CreateAnonymousUseCase
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.firebase.service.LogRepository
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.ui.component.JWDialogBoxModel
import com.akcay.justwatch.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val logRepository: LogRepository,
  private val signInUseCase: SignInUseCase,
  private val createAnonymousUseCase: CreateAnonymousUseCase
) : ViewModel() {
  val uiState = mutableStateOf(LoginUiState())

  val dialogState = mutableStateOf<JWDialogBoxModel?>(value = null)

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onLoginClick(openAndPopUp: (String, String) -> Unit) {
    showLoading()
    launchCatching(logRepository) {
      val result = signInUseCase(uiState.value.email, uiState.value.password)
      when(result) {
        is NetworkResult.Success -> {
          openAndPopUp.invoke(
            MainDestinations.HOME_ROUTE,
            MainDestinations.LOGIN_ROUTE
          )
        }
        is NetworkResult.Error -> {
          hideLoading()
        }
        is NetworkResult.Exception -> {
          hideLoading()
          dialogState.value = JWDialogBoxModel(mainColor = Red, title = "Error", description = result.e.message.toString(), positiveButtonText = "Ok")
        }
      }
    }
  }

  private fun hideLoading() {
    uiState.value = uiState.value.copy(isLoading = false)
  }

  private fun showLoading() {
    uiState.value = uiState.value.copy(isLoading = true)
  }

  fun createAnonymousUser(openAndPopUp: (String, String) -> Unit) {

    launchCatching(logRepository) {
      uiState.value = uiState.value.copy(isLoading = true)
      createAnonymousUseCase.invoke()
      openAndPopUp.invoke(
        MainDestinations.HOME_ROUTE,
        MainDestinations.LOGIN_ROUTE
      )
    }
  }
}