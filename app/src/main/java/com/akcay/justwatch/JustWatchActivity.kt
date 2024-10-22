package com.akcay.justwatch

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.akcay.justwatch.internal.util.JWLoadingManager
import com.akcay.justwatch.screens.splash.SplashScreenViewModel
import com.akcay.justwatch.internal.component.JWLoadingView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JustWatchActivity : ComponentActivity() {

    private val splashViewModel: SplashScreenViewModel by viewModels()

    @Inject
    lateinit var loadingState: JWLoadingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }

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
            val continueState by splashViewModel.onContinue.collectAsState()
            val isLoading by loadingState.isLoading.collectAsState()

            if (splashViewModel.checkDeviceIsRooted()) {
                splashViewModel.ErrorDialog {
                    finish()
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    splashViewModel.ShowNotificationPermission().apply {
                        if (continueState) {
                            JustWatchApp()
                            splashViewModel.setLoadingStatus(loading = false)
                        }
                    }
                } else {
                    JustWatchApp()
                    splashViewModel.setLoadingStatus(loading = false)
                }

                if (isLoading) {
                    JWLoadingView()
                }
            }
        }
    }
}