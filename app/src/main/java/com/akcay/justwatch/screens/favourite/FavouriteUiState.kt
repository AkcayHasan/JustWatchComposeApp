package com.akcay.justwatch.screens.favourite

import com.akcay.justwatch.data.local.entity.FavoriteMovie

data class FavouriteUiState(
    val favoriteMovies: List<FavoriteMovie> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
