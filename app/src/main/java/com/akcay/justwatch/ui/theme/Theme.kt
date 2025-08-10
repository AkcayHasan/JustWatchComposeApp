package com.akcay.justwatch.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.akcay.justwatch.internal.util.ThemeManager

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

    val animatedBg by animateColorAsState(target.background, label = "bg")

    CompositionLocalProvider(
        LocalJWColors provides target.copy(background = animatedBg),
        LocalJWTypography provides typography,
        LocalJWShapes provides shapes,
        LocalMyThemeController provides themeManager
    ) { content() }
}
