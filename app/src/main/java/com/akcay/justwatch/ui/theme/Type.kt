package com.akcay.justwatch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R

val displayFontFamily = FontFamily(
    Font(R.font.tt_regular, FontWeight.Normal),
    Font(R.font.tt_medium, FontWeight.Medium),
    Font(R.font.tt_bold, FontWeight.Bold),
    Font(R.font.tt_light, FontWeight.Light)
)

@Immutable
data class JWTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val body: TextStyle,
    val label: TextStyle
)

val JWDefaultTypography: JWTypography = JWTypography(
    h1 = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 34.sp
    ),
    h2 = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp
    ),
    body = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    ),
    label = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    )
)

fun JWTypography.toMaterialTypography(): Typography {
    return Typography(
        displayLarge = h1,
        displayMedium = h2,
        displaySmall = h2,
        headlineLarge = h1,
        headlineMedium = h2,
        headlineSmall = h2,
        titleLarge = h2,
        titleMedium = h2,
        titleSmall = h2,
        bodyLarge = body,
        bodyMedium = body,
        bodySmall = label,
        labelLarge = label,
        labelMedium = label,
        labelSmall = label
    )
}

