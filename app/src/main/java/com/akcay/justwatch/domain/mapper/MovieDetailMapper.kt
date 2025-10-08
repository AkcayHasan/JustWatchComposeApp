package com.akcay.justwatch.domain.mapper

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.domain.model.MovieDetail

fun MovieDetailResponse.toDomainModel(): MovieDetail {
    return MovieDetail(
        id = this.id.toLong(),
        title = this.title,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        runtime = this.runtime,
        genres = this.genres.map { it.name },
        tagline = this.tagline,
        status = this.status,
        budget = this.budget.toLong(),
        revenue = this.revenue.toLong(),
        homepage = this.homepage,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        popularity = this.popularity,
        video = this.video
    )
}
