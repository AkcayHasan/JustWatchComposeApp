package com.akcay.justwatch.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.akcay.justwatch.internal.util.ThemeManager
import com.akcay.justwatch.internal.util.LocalSystemBarsConfig
import com.akcay.justwatch.internal.util.createSystemBarsConfigWithTheme


private val LocalJWColors = staticCompositionLocalOf { lightScheme }
private val LocalJWTypography = staticCompositionLocalOf { JWDefaultTypography }
private val LocalJWShapes = staticCompositionLocalOf { Shapes() }

val LocalMyThemeController = compositionLocalOf<ThemeManager?> { null }

object JustWatchTheme {
    val colors: JWColors
        @Composable @ReadOnlyComposable
        get() = LocalJWColors.current

    val shapes: Shapes
        @Composable @ReadOnlyComposable
        get() = LocalJWShapes.current

    val typography: JWTypography
        @Composable @ReadOnlyComposable
        get() = LocalJWTypography.current
}

private fun JWColors.toLightColorScheme(): ColorScheme {
    return lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        error = error,
        onError = onError,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
    )
}

private fun JWColors.toDarkColorScheme(): ColorScheme {
    return darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        error = error,
        onError = onError,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
    )
}

@Composable
fun JustWatchTheme(
    colorsLight: JWColors = lightScheme,
    colorsDark: JWColors = darkScheme,
    typography: JWTypography = JWDefaultTypography,
    shapes: Shapes = Shapes(),
    themeManager: ThemeManager? = null,
    content: @Composable () -> Unit
) {
    val themeState = themeManager?.isDark?.collectAsState(initial = false)?.value ?: false

    val target = if (themeState) colorsDark else colorsLight
    val systemBarsConfig = createSystemBarsConfigWithTheme(themeState)

    val colorScheme = if (themeState) {
        target.toDarkColorScheme()
    } else {
        target.toLightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography.toMaterialTypography(),
        shapes = shapes
    ) {
        CompositionLocalProvider(
            LocalJWColors provides target,
            LocalJWTypography provides typography,
            LocalJWShapes provides shapes,
            LocalMyThemeController provides themeManager,
            LocalSystemBarsConfig provides systemBarsConfig
        ) { content() }
    }
}
