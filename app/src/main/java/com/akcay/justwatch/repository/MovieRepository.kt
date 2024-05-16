package com.akcay.justwatch.repository

import com.akcay.justwatch.data.model.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.model.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.model.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.data.model.listresponse.MovieResponse
import com.akcay.justwatch.util.NetworkResult

interface MovieRepository {

    suspend fun getAllPopularMovies(): NetworkResult<MovieResponse>

    suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetailResponse>

    suspend fun getMovieCastById(movieId: Long): NetworkResult<MovieDetailCreditsResponse>

    suspend fun getMovieVideoById(movieId: Long): NetworkResult<MovieVideoResponse>
}