package com.akcay.justwatch.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.usecase.GetUserProfileUseCase
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.NetworkResult
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
    private val accountRepository: AccountRepository,
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountScreenUiState(
        darkThemeChecked = runBlocking {
            dataStoreManager.getDarkThemeEnabled().first()
        }
    ))
    val uiState: StateFlow<AccountScreenUiState> = _uiState.asStateFlow()

    init {
        getDarkThemeStatus()
        loadUserProfile()
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

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            
            // Check if user is authenticated first
            if (!accountRepository.hasUser) {
                _uiState.update { 
                    it.copy(
                        loading = false,
                        error = "Please sign in to view your profile"
                    )
                }
                return@launch
            }
            
            try {
                when (val result = getUserProfileUseCase()) {
                    is NetworkResult.Success -> {
                        _uiState.update { 
                            it.copy(
                                loading = false,
                                user = result.data,
                                error = null
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { 
                            it.copy(
                                loading = false,
                                error = result.message ?: "Failed to load user profile"
                            )
                        }
                    }
                    is NetworkResult.Exception -> {
                        _uiState.update { 
                            it.copy(
                                loading = false,
                                error = result.e.message ?: "An error occurred"
                            )
                        }
                    }
                }
            } catch (e: SecurityException) {
                // Firebase SecurityException - Google Play Services issue
                _uiState.update { 
                    it.copy(
                        loading = false,
                        error = "Authentication service unavailable. Please check your Google Play Services."
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        loading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun refreshUserProfile() {
        loadUserProfile()
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                when (val result = accountRepository.signOut()) {
                    is NetworkResult.Success -> {
                        _uiState.update { 
                            it.copy(
                                user = null,
                                error = null,
                                shouldNavigateToLogin = true
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { 
                            it.copy(
                                error = result.message ?: "Failed to sign out"
                            )
                        }
                    }
                    is NetworkResult.Exception -> {
                        _uiState.update { 
                            it.copy(
                                error = result.e.message ?: "An error occurred during sign out"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Unknown error occurred during sign out"
                    )
                }
            }
        }
    }
    
    fun onNavigateToLoginHandled() {
        _uiState.update { it.copy(shouldNavigateToLogin = false) }
    }
}
