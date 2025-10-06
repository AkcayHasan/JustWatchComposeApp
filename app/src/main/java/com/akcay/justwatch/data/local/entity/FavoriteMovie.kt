package com.akcay.justwatch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey
    val id: Long,
    val userId: String,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val adult: Boolean,
    val originalLanguage: String,
    val popularity: Double,
    val video: Boolean,
    val addedAt: Long = System.currentTimeMillis()
)
