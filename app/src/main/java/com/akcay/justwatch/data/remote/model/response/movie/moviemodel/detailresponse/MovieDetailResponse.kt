package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieDetailResponse(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("budget") val budget: Int,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("homepage") val homepage: String,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdbId: String,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("revenue") val revenue: Int,
    @SerialName("runtime") val runtime: Int,
    @SerialName("status") val status: String,
    @SerialName("tagline") val tagline: String,
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)
