package com.akcay.justwatch.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.domain.usecase.GetAllFavoritesUseCase
import com.akcay.justwatch.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        loadFavorites()
    }
    
    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            try {
                getAllFavoritesUseCase().collect { favorites ->
                    _uiState.value = _uiState.value.copy(
                        favoriteMovies = favorites,
                        loading = false
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
                // Remove from favorites
                val currentFavorites = _uiState.value.favoriteMovies.toMutableList()
                currentFavorites.removeAll { it.id == movieId }
                _uiState.value = _uiState.value.copy(favoriteMovies = currentFavorites)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}