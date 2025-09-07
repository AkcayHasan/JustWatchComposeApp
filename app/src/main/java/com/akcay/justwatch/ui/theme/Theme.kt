package com.akcay.justwatch.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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

@Composable
private fun JWColors.animateColors(): JWColors {
    val animationSpec = tween<Color>(durationMillis = 300)
    
    val animatedPrimary by animateColorAsState(primary, animationSpec, label = "primary")
    val animatedOnPrimary by animateColorAsState(onPrimary, animationSpec, label = "onPrimary")
    val animatedPrimaryContainer by animateColorAsState(primaryContainer, animationSpec, label = "primaryContainer")
    val animatedOnPrimaryContainer by animateColorAsState(onPrimaryContainer, animationSpec, label = "onPrimaryContainer")
    val animatedSecondary by animateColorAsState(secondary, animationSpec, label = "secondary")
    val animatedOnSecondary by animateColorAsState(onSecondary, animationSpec, label = "onSecondary")
    val animatedSecondaryContainer by animateColorAsState(secondaryContainer, animationSpec, label = "secondaryContainer")
    val animatedOnSecondaryContainer by animateColorAsState(onSecondaryContainer, animationSpec, label = "onSecondaryContainer")
    val animatedTertiary by animateColorAsState(tertiary, animationSpec, label = "tertiary")
    val animatedOnTertiary by animateColorAsState(onTertiary, animationSpec, label = "onTertiary")
    val animatedError by animateColorAsState(error, animationSpec, label = "error")
    val animatedOnError by animateColorAsState(onError, animationSpec, label = "onError")
    val animatedBackground by animateColorAsState(background, animationSpec, label = "background")
    val animatedOnBackground by animateColorAsState(onBackground, animationSpec, label = "onBackground")
    val animatedSurface by animateColorAsState(surface, animationSpec, label = "surface")
    val animatedOnSurface by animateColorAsState(onSurface, animationSpec, label = "onSurface")
    val animatedOnSurfaceVariant by animateColorAsState(onSurfaceVariant, animationSpec, label = "onSurfaceVariant")

    return copy(
        primary = animatedPrimary,
        onPrimary = animatedOnPrimary,
        primaryContainer = animatedPrimaryContainer,
        onPrimaryContainer = animatedOnPrimaryContainer,
        secondary = animatedSecondary,
        onSecondary = animatedOnSecondary,
        secondaryContainer = animatedSecondaryContainer,
        onSecondaryContainer = animatedOnSecondaryContainer,
        tertiary = animatedTertiary,
        onTertiary = animatedOnTertiary,
        error = animatedError,
        onError = animatedOnError,
        background = animatedBackground,
        onBackground = animatedOnBackground,
        surface = animatedSurface,
        onSurface = animatedOnSurface,
        onSurfaceVariant = animatedOnSurfaceVariant
    )
}

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
    val animatedTarget = target.animateColors()

    val colorScheme = if (themeState) {
        animatedTarget.toDarkColorScheme()
    } else {
        animatedTarget.toLightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography.toMaterialTypography(),
        shapes = shapes
    ) {
        CompositionLocalProvider(
            LocalJWColors provides animatedTarget,
            LocalJWTypography provides typography,
            LocalJWShapes provides shapes,
            LocalMyThemeController provides themeManager
        ) { content() }
    }
}
