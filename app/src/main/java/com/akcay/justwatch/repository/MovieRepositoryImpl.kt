package com.akcay.justwatch.repository

import com.akcay.justwatch.data.model.MovieResponse
import com.akcay.justwatch.di.IoDispatcher
import com.akcay.justwatch.network.MovieService
import com.akcay.justwatch.util.NetworkResult
import com.akcay.justwatch.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
}