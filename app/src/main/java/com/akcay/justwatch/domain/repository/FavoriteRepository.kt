package com.akcay.justwatch.domain.repository

import com.akcay.justwatch.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<FavoriteMovie>>
    suspend fun getFavoriteById(movieId: Long): FavoriteMovie?
    suspend fun isFavorite(movieId: Long): Boolean
    suspend fun addToFavorites(favoriteMovie: FavoriteMovie)
    suspend fun removeFromFavorites(movieId: Long)
}
