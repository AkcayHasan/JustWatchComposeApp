package com.akcay.justwatch.data.model.detailresponse.videoresponse

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<Result>
)