package com.akcay.justwatch.screens.movies

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser

data class MoviesUiState(
  val isLoading: Boolean = false,
  val currentUser: AuthUser? = null
)