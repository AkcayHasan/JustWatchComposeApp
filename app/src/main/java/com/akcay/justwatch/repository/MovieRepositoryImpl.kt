package com.akcay.justwatch.repository

import com.akcay.justwatch.data.model.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.model.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.model.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.data.model.listresponse.MovieResponse
import com.akcay.justwatch.di.IoDispatcher
import com.akcay.justwatch.network.MovieService
import com.akcay.justwatch.util.NetworkResult
import com.akcay.justwatch.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val service: MovieService
): MovieRepository {

    override suspend fun getAllPopularMovies(): NetworkResult<MovieResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            service.getAllPopularMovies()
        }
    }

    override suspend fun getMovieById(movieId: Int): NetworkResult<MovieDetailResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            service.getMovieById(movieId)
        }
    }

    override suspend fun getMovieCastById(movieId: Int): NetworkResult<MovieDetailCreditsResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            service.getMovieCredits(movieId = movieId)
        }
    }

    override suspend fun getMovieVideoById(movieId: Int): NetworkResult<MovieVideoResponse> {
        return safeApiCall(defaultDispatcher = dispatcher) {
            service.getMovieVideo(movieId = movieId)
        }
    }

}