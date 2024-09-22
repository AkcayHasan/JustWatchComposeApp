package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<Result>
)