package com.akcay.justwatch.screens.splash

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.internal.component.JWDialogBox
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.JWSecurityUtil
import com.akcay.justwatch.internal.util.ThemeManager
import com.akcay.justwatch.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
  private val storeManager: DataStoreManager,
  private val themeManager: ThemeManager
) : ViewModel() {
  private val _uiState = MutableStateFlow(SplashUiState())
  val uiState: StateFlow<SplashUiState> = _uiState

  private val isCheckDevice = MutableStateFlow(false)
  private val isCheckOnboardingStatus = MutableStateFlow(false)
  private val isCheckAppTheme = MutableStateFlow(false)
  private val isCheckNotifications = MutableStateFlow(false)

  init {
    checkDeviceIsRooted()
    checkOnboardingStatus()
    checkAppTheme()

    viewModelScope.launch {
      combine(isCheckDevice, isCheckOnboardingStatus, isCheckAppTheme, isCheckNotifications) { s1, s2, s3, s4 ->
        s1 && s2 && s3 && s4
      }.collect { allOk ->
        _uiState.update {
          it.copy(canContinue = !allOk)
        }
      }
    }
  }

  private fun checkAppTheme() {
    viewModelScope.launch {
      val isDarkThemeEnable = storeManager.getDarkThemeEnabled().first()
      themeManager.setDarkThemeEnabled(isDarkThemeEnable)
      isCheckAppTheme.value = true
    }
  }

  private fun checkOnboardingStatus() {
    viewModelScope.launch {
      val shouldShowOnboarding = storeManager.shouldOnBoardingVisible()
      _uiState.update {
        it.copy(
          startDestination = if (shouldShowOnboarding) {
            MainDestinations.ONBOARDING_ROUTE
          } else {
            MainDestinations.LOGIN_ROUTE
          }
        )
      }
      isCheckOnboardingStatus.value = true
    }
  }

  private fun checkDeviceIsRooted() {
    _uiState.update { it.copy(isRooted = JWSecurityUtil.isDeviceRooted()) }
    isCheckDevice.value = true
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  @Composable
  fun ShowNotificationPermission() {
    val context = LocalContext.current

    val launcher =
      rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        isCheckNotifications.value = true
      }

    LaunchedEffect(key1 = Unit) {
      if (ContextCompat.checkSelfPermission(
          context,
          Manifest.permission.POST_NOTIFICATIONS
        ) == PermissionChecker.PERMISSION_GRANTED
      ) {
        isCheckNotifications.value = true
      } else {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
      }
    }
  }

  @Composable
  fun ErrorDialog(
    clickAction: () -> Unit
  ) {
    JWDialogBox(
      onDismissRequest = {},
      content = JWDialogBoxModel(
        mainColor = Red,
        title = "Hata!",
        description = "Rootlu cihaz ile giriş yapıyorsunuz!",
        positiveButtonText = "Çık!",
        negativeButtonText = null
      ),
      positiveButtonClickAction = {
        clickAction.invoke()
      }
    )
  }
}