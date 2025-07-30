package com.akcay.justwatch.domain.repository

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.screens.movies.data.response.MovieResponse
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAllPopularMovies(pageNumber: Int): Flow<NetworkResult<PageData<MovieUIModel>>>

    suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetailResponse>

    suspend fun getMovieCastById(movieId: Long): NetworkResult<MovieDetailCreditsResponse>

    suspend fun getMovieVideoById(movieId: Long): NetworkResult<MovieVideoResponse>
}
