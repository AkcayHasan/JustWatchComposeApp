package com.akcay.justwatch.data.remote.api

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.screens.movies.data.response.MovieResponse
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.util.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.encodedPath
import kotlin.text.append
import kotlin.toString

class MovieServiceImpl(
    private val httpClient: HttpClient,
) : MovieService {

    override suspend fun getAllPopularMovies(pageNumber: Int): NetworkResult<PageData<MovieResponse>> =
        makeApiCall {
            httpClient.get {
                url {
                    encodedPath = "movie/popular"
                    parameters.append("page", pageNumber.toString())
                }
            }
        }

    override suspend fun getTopRatedMovies(pageNumber: Int): NetworkResult<PageData<MovieResponse>> =
        makeApiCall {
            httpClient.get {
                url {
                    encodedPath = "movie/top_rated"
                    parameters.append("page", pageNumber.toString())
                }
            }
        }

    override suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetailResponse> =
        makeApiCall {
            httpClient.get {
                url {
                    encodedPath = "movie/$movieId"
                }
            }
        }

    override suspend fun getMovieCredits(movieId: Long): NetworkResult<MovieDetailCreditsResponse> =
        makeApiCall {
            httpClient.get {
                url {
                    encodedPath = "movie/$movieId/credits"
                }
            }
        }

    override suspend fun getMovieVideo(movieId: Long): NetworkResult<MovieVideoResponse> =
        makeApiCall {
            httpClient.get {
                url {
                    encodedPath = "movie/$movieId/videos"
                }
            }
        }
}
