package com.akcay.justwatch.ui.theme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

//Light Theme Colors
val primaryLight = Color(0xFF166684)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFC0E8FF)
val onPrimaryContainerLight = Color(0xFF004D66)

val secondaryLight = Color(0xFF4D616C)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFD0E6F3)
val onSecondaryContainerLight = Color(0xFF364954)

val tertiaryLight = Color(0xFF5E5A7D)
val onTertiaryLight = Color(0xFFFFFFFF)

val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)

val backgroundLight = Color(0xFFF6FAFE)
val onBackgroundLight = Color(0xFF171C1F)

val surfaceLight = Color(0xFFF6FAFE)
val onSurfaceLight = Color(0xFF171C1F)

//Dark Theme Colors
val primaryDark = Color(0xFF8DCFF1)
val onPrimaryDark = Color(0xFF003547)
val primaryContainerDark = Color(0xFF004D66)
val onPrimaryContainerDark = Color(0xFFC0E8FF)

val secondaryDark = Color(0xFFB5CAD6)
val onSecondaryDark = Color(0xFF1F333D)
val secondaryContainerDark = Color(0xFF364954)
val onSecondaryContainerDark = Color(0xFFD0E6F3)

val tertiaryDark = Color(0xFFC8C2EA)
val onTertiaryDark = Color(0xFF302C4C)

val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)

val backgroundDark = Color(0xFF0F1417)
val onBackgroundDark = Color(0xFFDFE3E7)

val surfaceDark = Color(0xFF0F1417)
val onSurfaceDark = Color(0xFFDFE3E7)

@Immutable
data class JWColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    val tertiary: Color,
    val onTertiary: Color,

    val error: Color,
    val onError: Color,

    val background: Color,
    val onBackground: Color,

    val surface: Color,
    val onSurface: Color,
)

val lightScheme = JWColors(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer= primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,

    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,

    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,

    error = errorLight,
    onError = onErrorLight,

    background = backgroundLight,
    onBackground = onBackgroundLight,

    surface = surfaceLight,
    onSurface = onSurfaceLight,
)

val darkScheme = JWColors(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer= primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,

    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,

    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,

    error = errorDark,
    onError = onErrorDark,

    background = backgroundDark,
    onBackground = onBackgroundDark,

    surface = surfaceDark,
    onSurface = onSurfaceDark,
)
