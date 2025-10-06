package com.akcay.justwatch.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.usecase.GetAllFavoritesUseCase
import com.akcay.justwatch.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
sealed interface FavouriteScreenViewEvent {
    data object DismissAuthDialog : FavouriteScreenViewEvent
    data object NavigateToLogin : FavouriteScreenViewEvent
    data object NavigateToRegister : FavouriteScreenViewEvent
}

sealed interface FavouriteScreenViewModelEvent {
    data object NavigateToLogin : FavouriteScreenViewModelEvent
    data object NavigateToRegister : FavouriteScreenViewModelEvent
}

class FavouriteViewModel(
    private val accountRepository: AccountRepository,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()
    
    private val channel = Channel<FavouriteScreenViewModelEvent>()
    val events = channel.receiveAsFlow()
    
    init {
        checkAuthAndLoadFavorites()
    }
    
    private fun checkAuthAndLoadFavorites() {
        viewModelScope.launch {
            accountRepository.currentAuthUser.collect { authUser ->
                if (authUser != null && (authUser.isAnonymous == false) && !authUser.email.isNullOrEmpty()) {
                    loadFavorites()
                } else {
                    _uiState.value = _uiState.value.copy(showAuthRequiredDialog = true)
                }
            }
        }
    }
    
    fun sendEvent(event: FavouriteScreenViewEvent) {
        when (event) {
            is FavouriteScreenViewEvent.DismissAuthDialog -> {
                _uiState.value = _uiState.value.copy(showAuthRequiredDialog = false)
            }
            is FavouriteScreenViewEvent.NavigateToLogin -> {
                _uiState.value = _uiState.value.copy(showAuthRequiredDialog = false)
                viewModelScope.launch {
                    channel.send(FavouriteScreenViewModelEvent.NavigateToLogin)
                }
            }
            is FavouriteScreenViewEvent.NavigateToRegister -> {
                _uiState.value = _uiState.value.copy(showAuthRequiredDialog = false)
                viewModelScope.launch {
                    channel.send(FavouriteScreenViewModelEvent.NavigateToRegister)
                }
            }
        }
    }
    
    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            try {
                val userId = accountRepository.currentUserId
                if (userId.isNotEmpty()) {
                    getAllFavoritesUseCase(userId).collect { favorites ->
                        _uiState.value = _uiState.value.copy(
                            favoriteMovies = favorites,
                            loading = false
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        error = "User not authenticated"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun removeFromFavorites(movieId: Long) {
        viewModelScope.launch {
            try {
                val userId = accountRepository.currentUserId
                if (userId.isNotEmpty()) {
                    // Remove from favorites
                    val currentFavorites = _uiState.value.favoriteMovies.toMutableList()
                    currentFavorites.removeAll { it.id == movieId }
                    _uiState.value = _uiState.value.copy(favoriteMovies = currentFavorites)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}