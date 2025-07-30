package com.akcay.justwatch.screens.movies.domain.mapper

import com.akcay.justwatch.screens.movies.data.response.MovieResponse
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel

fun MovieResponse.toUIModel() = MovieUIModel(
    id = this.id ?: 0L,
    title = this.title.orEmpty(),
    description = this.overview.orEmpty(),
    image = this.posterPath.orEmpty(),
)
