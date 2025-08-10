package com.akcay.justwatch.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val themeManager: ThemeManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountScreenUiState(
        darkThemeChecked = runBlocking {
            dataStoreManager.getDarkThemeEnabled().first()
        }
    ))
    val uiState: StateFlow<AccountScreenUiState> = _uiState.asStateFlow()

    init {
        getDarkThemeStatus()
    }

    fun darkThemeCheckedChange(isChecked: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setDarkThemeEnabled(isChecked)
            themeManager.toggle(isChecked)
        }
    }

    private fun getDarkThemeStatus() {
        viewModelScope.launch {
            dataStoreManager.getDarkThemeEnabled().collect { isChecked ->
                _uiState.update {
                    it.copy(darkThemeChecked = isChecked)
                }
            }
        }
    }
}
