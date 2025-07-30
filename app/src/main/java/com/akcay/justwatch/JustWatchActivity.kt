package com.akcay.justwatch

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.akcay.justwatch.internal.component.JWDialogBox
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.internal.navigation.AppDestination
import com.akcay.justwatch.internal.util.LocalThemeManager
import com.akcay.justwatch.internal.util.ThemeManager
import com.akcay.justwatch.screens.splash.SplashScreenViewModel
import com.akcay.justwatch.ui.theme.Red
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JustWatchActivity : ComponentActivity() {

    private val splashViewModel: SplashScreenViewModel by viewModels()

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_JustWatch)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            ),
        )
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(LocalThemeManager provides themeManager) {
                JustWatchContent(
                    splashViewModel = splashViewModel,
                    onAppReady = { destination ->
                        JustWatchApp(
                            startDestination = destination,
                        )
                    },
                    onError = {
                        finish()
                    },
                )
            }
        }

        askNotificationPermission()
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ -> }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) return

        if (!shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(POST_NOTIFICATIONS)
        }
    }
}

@Composable
fun JustWatchContent(
    splashViewModel: SplashScreenViewModel,
    onAppReady: @Composable (AppDestination) -> Unit,
    onError: () -> Unit,
) {
    val destination by splashViewModel.navigationDestination.collectAsState()
    val isRooted by splashViewModel.isRooted.collectAsState()

    destination?.let { onAppReady.invoke(it) }

    if (isRooted) {
        ErrorDialog(
            clickAction = onError
        )
    }
}

@Composable
fun ErrorDialog(
    clickAction: () -> Unit,
) {
    JWDialogBox(
        onDismissRequest = {},
        content = JWDialogBoxModel(
            mainColor = Red,
            title = "Hata!",
            description = "Rootlu cihaz ile giriş yapıyorsunuz!",
            positiveButtonText = "Çık!",
            negativeButtonText = null,
        ),
        positiveButtonClickAction = {
            clickAction.invoke()
        },
    )
}
