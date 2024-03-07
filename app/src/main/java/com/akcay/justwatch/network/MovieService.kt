package com.akcay.justwatch.network

import com.akcay.justwatch.data.model.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.model.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.model.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.data.model.listresponse.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailCreditsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int
    ): Response<MovieVideoResponse>
}