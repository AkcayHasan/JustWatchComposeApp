package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieDetailCreditsResponse(
    @SerialName("cast") val cast: List<Cast>,
    @SerialName("crew") val crew: List<Crew>,
    @SerialName("id") val id: Int
)
