package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Result(
    @SerialName("id") val id: String,
    @SerialName("iso_3166_1") val iso_3166_1: String,
    @SerialName("iso_639_1") val iso_639_1: String,
    @SerialName("key") val key: String,
    @SerialName("name") val name: String,
    @SerialName("official") val official: Boolean,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("site") val site: String,
    @SerialName("size") val size: Int,
    @SerialName("type") val type: String
)
