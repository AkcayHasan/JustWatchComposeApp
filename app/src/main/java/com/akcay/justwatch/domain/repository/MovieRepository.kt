package com.akcay.justwatch.domain.repository

import com.akcay.justwatch.domain.model.MovieCredits
import com.akcay.justwatch.domain.model.MovieDetail
import com.akcay.justwatch.domain.model.MovieVideos
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAllPopularMovies(pageNumber: Int): Flow<NetworkResult<PageData<MovieUIModel>>>

    fun getTopRatedMovies(pageNumber: Int): Flow<NetworkResult<PageData<MovieUIModel>>>

    suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetail>

    suspend fun getMovieCastById(movieId: Long): NetworkResult<MovieCredits>

    suspend fun getMovieVideoById(movieId: Long): NetworkResult<MovieVideos>
}
