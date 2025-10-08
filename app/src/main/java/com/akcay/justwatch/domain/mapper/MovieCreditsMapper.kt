package com.akcay.justwatch.domain.mapper

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.domain.model.MovieCredits
import com.akcay.justwatch.domain.model.MovieCast
import com.akcay.justwatch.domain.model.MovieCrew

fun MovieDetailCreditsResponse.toDomainModel(): MovieCredits {
    return MovieCredits(
        cast = this.cast.map { cast ->
            MovieCast(
                id = cast.id.toLong(),
                name = cast.name,
                character = cast.character,
                profilePath = cast.profilePath,
                order = cast.order,
                knownForDepartment = cast.knownForDepartment
            )
        },
        crew = this.crew.map { crew ->
            MovieCrew(
                id = crew.id.toLong(),
                name = crew.name,
                job = crew.job,
                department = crew.department,
                profilePath = crew.profilePath
            )
        }
    )
}
