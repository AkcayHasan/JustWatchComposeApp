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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.traceEventStart
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.akcay.justwatch.internal.util.LocalThemeManager
import com.akcay.justwatch.internal.util.ThemeManager
import com.akcay.justwatch.screens.splash.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JustWatchActivity : ComponentActivity() {

  private val splashViewModel: SplashScreenViewModel by viewModels()

  @Inject
  lateinit var themeManager: ThemeManager

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().setKeepOnScreenCondition {
      splashViewModel.uiState.value.canContinue
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
      CompositionLocalProvider(LocalThemeManager provides themeManager) {
        JustWatchContent(
          splashViewModel = splashViewModel,
          onAppReady = { destination ->
            JustWatchApp(
              startDestination = destination
            )
          },
          onError = {
            finish()
          }
        )
      }
    }
  }
}

@Composable
fun JustWatchContent(
  splashViewModel: SplashScreenViewModel,
  onAppReady: @Composable (String) -> Unit,
  onError: () -> Unit
) {
  val uiState by splashViewModel.uiState.collectAsState()

  when {
    uiState.isRooted -> {
      splashViewModel.ErrorDialog(onError)
    }

    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
      splashViewModel.ShowNotificationPermission()
      if (!uiState.canContinue) {
        onAppReady(uiState.startDestination)
      }
    }

    uiState.canContinue -> {
      onAppReady(uiState.startDestination)
    }
  }

}