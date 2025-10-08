package com.akcay.justwatch.data.remote.api

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.screens.movies.data.response.MovieResponse
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.util.NetworkResult

interface MovieService {

    suspend fun getAllPopularMovies(
        pageNumber: Int
    ): NetworkResult<PageData<MovieResponse>>

    suspend fun getTopRatedMovies(
        pageNumber: Int
    ): NetworkResult<PageData<MovieResponse>>

    suspend fun getMovieById(
        movieId: Long
    ): NetworkResult<MovieDetailResponse>

    suspend fun getMovieCredits(
        movieId: Long
    ): NetworkResult<MovieDetailCreditsResponse>

    suspend fun getMovieVideo(
        movieId: Long
    ): NetworkResult<MovieVideoResponse>
}
