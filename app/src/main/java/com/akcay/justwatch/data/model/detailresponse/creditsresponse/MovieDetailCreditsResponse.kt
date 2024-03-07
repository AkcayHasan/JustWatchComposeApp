package com.akcay.justwatch.data.model.detailresponse.creditsresponse

import com.google.gson.annotations.SerializedName

data class MovieDetailCreditsResponse(
    @SerializedName("cast") val cast: List<Cast>,
    @SerializedName("crew") val crew: List<Crew>,
    @SerializedName("id") val id: Int
)