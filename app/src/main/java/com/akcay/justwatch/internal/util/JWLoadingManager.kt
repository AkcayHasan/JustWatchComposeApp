package com.akcay.justwatch.internal.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JWLoadingManager {
  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  fun showLoading() {
    _isLoading.value = true
  }

  fun hideLoading() {
    _isLoading.value = false
  }
}