package com.akcay.justwatch.domain.repository

import androidx.paging.PagingData
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.internal.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPagedPopularMovies(): Flow<PagingData<MovieResult>>

    fun getAllPopularMovies(pageNumber: Int): Flow<NetworkResult<MovieResponse>>

    suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetailResponse>

    suspend fun getMovieCastById(movieId: Long): NetworkResult<MovieDetailCreditsResponse>

    suspend fun getMovieVideoById(movieId: Long): NetworkResult<MovieVideoResponse>
}