package com.akcay.justwatch

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.akcay.justwatch.screens.splash.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JustWatchActivity : ComponentActivity() {

  private val splashViewModel: SplashScreenViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().setKeepOnScreenCondition {
      splashViewModel.isLoading.value
    }
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.light(
        Color.TRANSPARENT,
        Color.TRANSPARENT
      ),
      navigationBarStyle = SystemBarStyle.light(
        Color.TRANSPARENT,
        Color.TRANSPARENT
      )
    )

    setContent {
      JustWatchContent(
        splashViewModel = splashViewModel,
        onAppReady = {
          JustWatchApp(startDestination = it)
        },
        onError = {
          finish()
        }
      )
    }
  }
}

@Composable
fun JustWatchContent(
  splashViewModel: SplashScreenViewModel,
  onAppReady: @Composable (String) -> Unit,
  onError: () -> Unit
) {
  val startDestination by splashViewModel.startDestination.collectAsState()
  val continueState by splashViewModel.onContinue.collectAsState()

  when {
    splashViewModel.checkDeviceIsRooted() -> {
      splashViewModel.ErrorDialog(onError)
    }
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
      splashViewModel.ShowNotificationPermission()
      if (continueState) {
        onAppReady(startDestination)
        splashViewModel.setLoadingStatus(false)
      }
    }
    else -> {
      onAppReady(startDestination)
      splashViewModel.setLoadingStatus(false)
    }
  }

}