package com.akcay.justwatch.data.repository

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.internal.di.IoDispatcher
import com.akcay.justwatch.data.remote.api.MovieService
import com.akcay.justwatch.data.remote.datasource.MovieRemoteDataSource
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override fun getAllPopularMovies(): Flow<NetworkResult<MovieResponse>> = flow {
        emit(
            safeApiCall(defaultDispatcher = dispatcher) {
                remoteDataSource.getAllPopularMovies()
            }
        )
    }

    override suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetailResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            remoteDataSource.getMovieById(movieId)
        }
    }

    override suspend fun getMovieCastById(movieId: Long): NetworkResult<MovieDetailCreditsResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            remoteDataSource.getMovieCredits(id = movieId)
        }
    }

    override suspend fun getMovieVideoById(movieId: Long): NetworkResult<MovieVideoResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            remoteDataSource.getMovieVideo(id = movieId)
        }
    }

}