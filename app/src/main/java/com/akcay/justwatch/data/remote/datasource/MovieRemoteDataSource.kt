package com.akcay.justwatch.data.remote.datasource

import com.akcay.justwatch.data.remote.api.MovieService
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun getAllPopularMovies() = movieService.getAllPopularMovies()

    suspend fun getMovieById(id: Long) = movieService.getMovieById(id)

    suspend fun getMovieCredits(id: Long) = movieService.getMovieCredits(id)

    suspend fun getMovieVideo(id: Long) = movieService.getMovieVideo(id)
}