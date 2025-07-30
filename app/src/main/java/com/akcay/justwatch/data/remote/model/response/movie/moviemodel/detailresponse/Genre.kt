package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
