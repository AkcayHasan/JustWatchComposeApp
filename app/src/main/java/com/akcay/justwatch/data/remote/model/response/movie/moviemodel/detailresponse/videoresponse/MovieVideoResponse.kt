package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieVideoResponse(
    @SerialName("id") val id: Int,
    @SerialName("results") val results: List<Result>
)
