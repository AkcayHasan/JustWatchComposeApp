package com.akcay.justwatch.data.remote.datasource

import com.akcay.justwatch.data.remote.api.MovieService
class MovieRemoteDataSource(
    private val movieService: MovieService,
) {

    suspend fun getAllPopularMovies(pageNumber: Int) =
        movieService.getAllPopularMovies(pageNumber = pageNumber)

    suspend fun getTopRatedMovies(pageNumber: Int) =
        movieService.getTopRatedMovies(pageNumber = pageNumber)

    suspend fun getMovieById(id: Long) = movieService.getMovieById(id)

    suspend fun getMovieCredits(id: Long) = movieService.getMovieCredits(id)

    suspend fun getMovieVideo(id: Long) = movieService.getMovieVideo(id)
}
