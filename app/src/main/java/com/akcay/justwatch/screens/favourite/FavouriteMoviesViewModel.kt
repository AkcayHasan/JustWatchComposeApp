package com.akcay.justwatch.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
  private val dataStoreManager: DataStoreManager,
  private val logRepository: LogRepository,
  private val themeManager: ThemeManager
) : ViewModel() {

  private val _uiState = MutableStateFlow(FavouriteMoviesUiState())
  val uiState: StateFlow<FavouriteMoviesUiState> = _uiState

  init {
    getDarkThemeEnabled()
  }

  fun darkThemeCheckedChange(isChecked: Boolean) {
    launchCatching(logRepository) {
      _uiState.update { _uiState.value.copy(darkThemeChecked = isChecked) }
      dataStoreManager.setDarkThemeEnabled(isChecked)
      themeManager.setDarkThemeEnabled(isChecked)
    }
  }

  private fun getDarkThemeEnabled() {
    showLoading()
    viewModelScope.launch {
      dataStoreManager.getDarkThemeEnabled().collect { isEnabled ->
        hideLoading()
        _uiState.update { _uiState.value.copy(darkThemeChecked = isEnabled) }
      }
    }
  }

  private fun showLoading() {
    _uiState.update { _uiState.value.copy(loading = true) }
  }

  private fun hideLoading() {
    _uiState.update { _uiState.value.copy(loading = false) }
  }
}