package com.akcay.justwatch.data.local.dao

import androidx.room.*
import com.akcay.justwatch.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    
    @Query("SELECT * FROM favorite_movies WHERE userId = :userId ORDER BY addedAt DESC")
    fun getAllFavorites(userId: String): Flow<List<FavoriteMovie>>
    
    @Query("SELECT * FROM favorite_movies WHERE id = :movieId AND userId = :userId")
    suspend fun getFavoriteById(movieId: Long, userId: String): FavoriteMovie?
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId AND userId = :userId)")
    suspend fun isFavorite(movieId: Long, userId: String): Boolean
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteMovie: FavoriteMovie)
    
    @Delete
    suspend fun deleteFavorite(favoriteMovie: FavoriteMovie)
    
    @Query("DELETE FROM favorite_movies WHERE id = :movieId AND userId = :userId")
    suspend fun deleteFavoriteById(movieId: Long, userId: String)
    
    @Query("DELETE FROM favorite_movies WHERE userId = :userId")
    suspend fun deleteAllFavoritesForUser(userId: String)
    
    @Query("DELETE FROM favorite_movies")
    suspend fun deleteAllFavorites()
}
