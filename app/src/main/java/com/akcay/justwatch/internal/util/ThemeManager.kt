package com.akcay.justwatch.internal.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor() {

  private val _isDarkThemeEnabled = MutableStateFlow(false)
  val isDarkThemeEnabled: StateFlow<Boolean> = _isDarkThemeEnabled.asStateFlow()

  fun setDarkThemeEnabled(enabled: Boolean) {
    _isDarkThemeEnabled.value = enabled
  }
}