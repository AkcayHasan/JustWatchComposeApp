package com.akcay.justwatch.repository

import com.akcay.justwatch.data.model.MovieResponse
import com.akcay.justwatch.util.NetworkResult

interface MovieRepository {

    suspend fun getAllPopularMovies(): NetworkResult<MovieResponse>

    //suspend fun getMovieDetailInfo(): NetworkResult<>
}