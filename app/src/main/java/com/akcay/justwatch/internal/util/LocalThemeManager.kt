package com.akcay.justwatch.internal.util

import androidx.compose.runtime.compositionLocalOf

val LocalThemeManager = compositionLocalOf<ThemeManager> {
  error("ThemeManager not provided")
}
