package com.akcay.justwatch.network

import com.akcay.justwatch.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(): Response<MovieResponse>

}