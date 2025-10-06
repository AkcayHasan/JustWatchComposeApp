package com.akcay.justwatch.internal.util

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.akcay.justwatch.internal.ext.asActivity

/**
 * System bars configuration data class
 */
data class SystemBarsConfig(
    val statusBarsLight: Boolean,
    val navigationBarsLight: Boolean
)

/**
 * CompositionLocal for system bars configuration
 */
val LocalSystemBarsConfig = compositionLocalOf { 
    SystemBarsConfig(
        statusBarsLight = false,
        navigationBarsLight = false
    )
}

@Composable
fun SetSystemBarsLightAppearance(
    isAppearanceLightStatusBars: Boolean = false,
    isAppearanceLightNavigationBars: Boolean = true
) {
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = isAppearanceLightStatusBars, key2 = isAppearanceLightNavigationBars) {
        context.asActivity()?.let { activity ->
            WindowCompat.getInsetsController(
                activity.window,
                activity.window.decorView
            ).apply {
                this.isAppearanceLightStatusBars = isAppearanceLightStatusBars
                this.isAppearanceLightNavigationBars = isAppearanceLightNavigationBars
            }
        }
    }
}

/**
 * Generic system bars configuration - uses CompositionLocal for dynamic theming
 */
@Composable
fun SetSystemBarsForScreen(
    config: SystemBarsConfig? = null,
    forceLightStatusBars: Boolean? = null,
    forceLightNavigationBars: Boolean? = null
) {
    val context: Context = LocalContext.current
    val systemBarsConfig = config ?: LocalSystemBarsConfig.current
    
    val statusBarsLight = forceLightStatusBars ?: systemBarsConfig.statusBarsLight
    val navigationBarsLight = forceLightNavigationBars ?: systemBarsConfig.navigationBarsLight
    
    // Use systemBarsConfig as key to ensure recomposition when theme changes
    LaunchedEffect(key1 = systemBarsConfig) {
        context.asActivity()?.let { activity ->
            WindowCompat.getInsetsController(
                activity.window,
                activity.window.decorView
            ).apply {
                this.isAppearanceLightStatusBars = statusBarsLight
                this.isAppearanceLightNavigationBars = navigationBarsLight
            }
        }
    }
}

/**
 * Helper function to create theme-aware system bars config
 * This function is reactive to theme changes
 */
@Composable
fun createSystemBarsConfig(
    statusBarsLight: Boolean? = null,
    navigationBarsLight: Boolean? = null
): SystemBarsConfig {
    val isDarkTheme = isSystemInDarkTheme()
    
    return SystemBarsConfig(
        statusBarsLight = statusBarsLight ?: !isDarkTheme,
        navigationBarsLight = navigationBarsLight ?: !isDarkTheme
    )
}

/**
 * Helper function to create system bars config with explicit theme state
 * This function is used when we have direct access to theme state
 */
fun createSystemBarsConfigWithTheme(
    isDarkTheme: Boolean,
    statusBarsLight: Boolean? = null,
    navigationBarsLight: Boolean? = null
): SystemBarsConfig {
    return SystemBarsConfig(
        statusBarsLight = statusBarsLight ?: !isDarkTheme,
        navigationBarsLight = navigationBarsLight ?: !isDarkTheme
    )
}

