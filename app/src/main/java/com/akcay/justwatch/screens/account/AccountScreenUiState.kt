package com.akcay.justwatch.screens.account

import com.akcay.justwatch.data.remote.model.User

data class AccountScreenUiState(
  val loading: Boolean = false,
  val darkThemeChecked: Boolean = false,
  val user: User? = null,
  val error: String? = null,
  val shouldNavigateToLogin: Boolean = false,
)
