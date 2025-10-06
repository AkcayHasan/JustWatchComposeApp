package com.akcay.justwatch.data.local.repository

import com.akcay.justwatch.data.local.dao.FavoriteMovieDao
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
class FavoriteRepositoryImpl(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteRepository {
    
    override fun getAllFavorites(userId: String): Flow<List<FavoriteMovie>> {
        return favoriteMovieDao.getAllFavorites(userId)
    }
    
    override suspend fun getFavoriteById(movieId: Long, userId: String): FavoriteMovie? {
        return favoriteMovieDao.getFavoriteById(movieId, userId)
    }
    
    override suspend fun isFavorite(movieId: Long, userId: String): Boolean {
        return favoriteMovieDao.isFavorite(movieId, userId)
    }
    
    override suspend fun addToFavorites(favoriteMovie: FavoriteMovie) {
        favoriteMovieDao.insertFavorite(favoriteMovie)
    }
    
    override suspend fun removeFromFavorites(movieId: Long, userId: String) {
        favoriteMovieDao.deleteFavoriteById(movieId, userId)
    }
}
