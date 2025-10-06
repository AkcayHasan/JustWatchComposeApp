package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoritesUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(userId: String): Flow<List<FavoriteMovie>> {
        return favoriteRepository.getAllFavorites(userId)
    }
}
