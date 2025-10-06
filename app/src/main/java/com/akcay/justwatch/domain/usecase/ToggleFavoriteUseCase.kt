package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(favoriteMovie: FavoriteMovie, userId: String): Boolean {
        val isFavorite = favoriteRepository.isFavorite(favoriteMovie.id, userId)
        return if (isFavorite) {
            favoriteRepository.removeFromFavorites(favoriteMovie.id, userId)
            false
        } else {
            favoriteRepository.addToFavorites(favoriteMovie)
            true
        }
    }
}
