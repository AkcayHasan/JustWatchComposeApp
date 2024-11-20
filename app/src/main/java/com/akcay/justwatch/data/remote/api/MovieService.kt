package com.akcay.justwatch.data.remote.api

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("page") pageNumber: Int
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Long
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Long
    ): Response<MovieDetailCreditsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Long
    ): Response<MovieVideoResponse>
}