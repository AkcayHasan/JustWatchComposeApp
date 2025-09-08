package com.akcay.justwatch.data.local.repository

import com.akcay.justwatch.data.local.dao.FavoriteMovieDao
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import com.akcay.justwatch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteRepository {
    
    override fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return favoriteMovieDao.getAllFavorites()
    }
    
    override suspend fun getFavoriteById(movieId: Long): FavoriteMovie? {
        return favoriteMovieDao.getFavoriteById(movieId)
    }
    
    override suspend fun isFavorite(movieId: Long): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }
    
    override suspend fun addToFavorites(favoriteMovie: FavoriteMovie) {
        favoriteMovieDao.insertFavorite(favoriteMovie)
    }
    
    override suspend fun removeFromFavorites(movieId: Long) {
        favoriteMovieDao.deleteFavoriteById(movieId)
    }
}
