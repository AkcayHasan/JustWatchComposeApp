package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.domain.repository.FavoriteRepository
class IsFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Long, userId: String): Boolean {
        return favoriteRepository.isFavorite(movieId, userId)
    }
}
