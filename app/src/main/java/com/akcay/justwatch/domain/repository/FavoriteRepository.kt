package com.akcay.justwatch.domain.repository

import com.akcay.justwatch.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(userId: String): Flow<List<FavoriteMovie>>
    suspend fun getFavoriteById(movieId: Long, userId: String): FavoriteMovie?
    suspend fun isFavorite(movieId: Long, userId: String): Boolean
    suspend fun addToFavorites(favoriteMovie: FavoriteMovie)
    suspend fun removeFromFavorites(movieId: Long, userId: String)
}
