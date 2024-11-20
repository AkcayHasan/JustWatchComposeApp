package com.akcay.justwatch.screens.movies

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult

data class MoviesUiState(
  val user: User? = null,
  //val movieList: MovieResponse? = null,
  val page: Int? = 1,
  val movieList: List<MovieResult>? = null,
  val loading: Boolean = false
)