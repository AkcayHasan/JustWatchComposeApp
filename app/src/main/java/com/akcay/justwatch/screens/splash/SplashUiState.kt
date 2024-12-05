package com.akcay.justwatch.screens.splash

import com.akcay.justwatch.MainDestinations

data class SplashUiState(
  val isRooted: Boolean = false,
  val canContinue: Boolean = true,
  val startDestination: String = MainDestinations.LOGIN_ROUTE,
  val isDarkThemeEnable: Boolean = false
)