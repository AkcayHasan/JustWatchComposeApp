package com.akcay.justwatch.screens.detail

import com.akcay.justwatch.domain.model.MovieCredits
import com.akcay.justwatch.domain.model.MovieDetail
import com.akcay.justwatch.domain.model.MovieVideos

data class MovieDetailUiState(
    val loadingState: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val movieCast: MovieCredits? = null,
    val movieTrailers: MovieVideos? = null,
    val isFavorite: Boolean = false,
)

sealed interface MovieDetailScreenViewEvent {
    data object FavoriteIconClicked: MovieDetailScreenViewEvent
}
