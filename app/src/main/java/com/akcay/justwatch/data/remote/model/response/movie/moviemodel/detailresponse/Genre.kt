package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)
