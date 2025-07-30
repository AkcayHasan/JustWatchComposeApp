package com.akcay.justwatch.screens.movies.ui

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.screens.movies.data.response.MovieResponse
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel



data class MoviesUiState(
    val user: User? = null,
    val movieList: List<MovieUIModel> = emptyList(),
    val loading: Boolean = false
)
