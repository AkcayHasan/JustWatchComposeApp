package com.akcay.justwatch.domain.model

data class MovieDetail(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int,
    val genres: List<String>,
    val tagline: String,
    val status: String,
    val budget: Long,
    val revenue: Long,
    val homepage: String,
    val adult: Boolean,
    val originalLanguage: String,
    val popularity: Double,
    val video: Boolean
)
