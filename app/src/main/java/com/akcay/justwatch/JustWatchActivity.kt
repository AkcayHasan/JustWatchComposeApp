package com.akcay.justwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.akcay.justwatch.screens.home.HomeScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JustWatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JustWatchTheme(darkTheme = false) {
                JustWatchNavigation()
            }
        }
    }
}