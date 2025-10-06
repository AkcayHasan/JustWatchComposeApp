package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Long, userId: String): Boolean {
        return favoriteRepository.isFavorite(movieId, userId)
    }
}
