package com.akcay.justwatch.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.domain.usecase.CreateAnonymousUseCase
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val logRepository: LogRepository,
  private val dataStoreManager: DataStoreManager,
  private val signInUseCase: SignInUseCase,
  private val createAnonymousUseCase: CreateAnonymousUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow(LoginUiState())
  val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

  val dialogState = mutableStateOf<JWDialogBoxModel?>(value = null)

  init {
    showLoading()
    viewModelScope.launch {
      dataStoreManager.apply {
        if (!getRememberedPassword().isNullOrEmpty() && !getRememberedEmail().isNullOrEmpty() && getRememberMeCheckboxStatus()) {
          _uiState.update {
            _uiState.value.copy(
              email = getRememberedEmail() ?: "",
              password = getRememberedPassword() ?: "",
              isRememberCheckboxChecked = true
            )
          }
        }
        hideLoading()
      }
    }
  }

  fun onEmailChange(newValue: String) {
    _uiState.update { _uiState.value.copy(email = newValue) }
  }

  fun onPasswordChange(newValue: String) {
    _uiState.update {
      _uiState.value.copy(password = newValue)
    }
  }

  fun onLoginClick(openAndPopUp: (String, String) -> Unit, isRememberMeChecked: Boolean) {
    launchCatching(logRepository) {
      showLoading()
      try {
        when (val result = signInUseCase(uiState.value.email, uiState.value.password)) {
          is NetworkResult.Success -> {
            dataStoreManager.apply {
              if (isRememberMeChecked) {
                saveRememberedEmail(uiState.value.email)
                saveRememberedPassword(uiState.value.password)
              } else {
                clearRememberedEmail()
                clearRememberedPassword()
              }
              saveRememberMeCheckboxStatus(isRememberMeChecked)
            }
            openAndPopUp.invoke(
              MainDestinations.HOME_ROUTE,
              MainDestinations.LOGIN_ROUTE
            )
          }

          is NetworkResult.Error -> {
          }

          is NetworkResult.Exception -> {
            showDialog(result.e.message.toString())
          }
        }
      } finally {
        hideLoading()
      }
    }
  }

  private fun showDialog(desc: String) {
    dialogState.value = JWDialogBoxModel(
      mainColor = Red,
      title = "Error",
      description = desc,
      positiveButtonText = "Ok"
    )
  }

  fun closeDialog() {
    dialogState.value = null
  }

  private fun showLoading() = _uiState.update { _uiState.value.copy(loading = true) }
  private fun hideLoading() = _uiState.update { _uiState.value.copy(loading = false) }

  fun createAnonymousUser(openAndPopUp: (String, String) -> Unit) {

    launchCatching(logRepository) {
      showLoading()
      createAnonymousUseCase.invoke()
      openAndPopUp.invoke(
        MainDestinations.HOME_ROUTE,
        MainDestinations.LOGIN_ROUTE
      )
    }
  }
}