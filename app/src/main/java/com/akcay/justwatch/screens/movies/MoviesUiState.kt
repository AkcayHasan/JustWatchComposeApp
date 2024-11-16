package com.akcay.justwatch.screens.movies

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse

data class MoviesUiState(
  val user: User? = null,
  val movieList: MovieResponse? = null
)