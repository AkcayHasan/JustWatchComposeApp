package com.akcay.justwatch.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.domain.usecase.CreateAnonymousUseCase
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.internal.util.JWLoadingManager
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loadingState: JWLoadingManager,
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
    loadingState.showLoading()
    launchCatching(logRepository) {
      when(val result = signInUseCase(uiState.value.email, uiState.value.password)) {
        is NetworkResult.Success -> {
          loadingState.hideLoading()
          openAndPopUp.invoke(
            MainDestinations.HOME_ROUTE,
            MainDestinations.LOGIN_ROUTE
          )
        }
        is NetworkResult.Error -> {
          loadingState.hideLoading()
        }
        is NetworkResult.Exception -> {
          loadingState.hideLoading()
          showDialog(result.e.message.toString())
        }
      }
    }
  }

  private fun showDialog(desc: String) {
    dialogState.value = JWDialogBoxModel(mainColor = Red, title = "Error", description = desc, positiveButtonText = "Ok")
  }

  fun closeDialog() {
    dialogState.value = null
  }

  fun createAnonymousUser(openAndPopUp: (String, String) -> Unit) {

    launchCatching(logRepository) {
      loadingState.showLoading()
      createAnonymousUseCase.invoke()
      openAndPopUp.invoke(
        MainDestinations.HOME_ROUTE,
        MainDestinations.LOGIN_ROUTE
      )
    }
  }
}