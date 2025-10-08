package com.akcay.justwatch.domain.model

data class MovieCast(
    val id: Long,
    val name: String,
    val character: String,
    val profilePath: String?,
    val order: Int,
    val knownForDepartment: String
)

data class MovieCrew(
    val id: Long,
    val name: String,
    val job: String,
    val department: String,
    val profilePath: String?
)

data class MovieCredits(
    val cast: List<MovieCast>,
    val crew: List<MovieCrew>
)
