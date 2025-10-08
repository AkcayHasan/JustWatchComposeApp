package com.akcay.justwatch.data.remote.datasource

import com.akcay.justwatch.data.remote.api.MovieService
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.screens.movies.data.response.MovieResponse
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.util.NetworkResult

class MovieRemoteDataSource(
    private val movieService: MovieService,
) {

    suspend fun getAllPopularMovies(pageNumber: Int): NetworkResult<PageData<MovieResponse>> {
        return movieService.getAllPopularMovies(pageNumber = pageNumber)
    }

    suspend fun getTopRatedMovies(pageNumber: Int): NetworkResult<PageData<MovieResponse>> {
        return movieService.getTopRatedMovies(pageNumber = pageNumber)
    }

    suspend fun getMovieById(id: Long): NetworkResult<MovieDetailResponse> {
        return movieService.getMovieById(id)
    }

    suspend fun getMovieCredits(id: Long): NetworkResult<MovieDetailCreditsResponse> {
        return movieService.getMovieCredits(id)
    }

    suspend fun getMovieVideo(id: Long): NetworkResult<MovieVideoResponse> {
        return movieService.getMovieVideo(id)
    }
}