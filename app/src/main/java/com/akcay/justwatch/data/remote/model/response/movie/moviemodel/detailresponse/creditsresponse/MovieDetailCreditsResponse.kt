package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse

import com.google.gson.annotations.SerializedName

data class MovieDetailCreditsResponse(
    @SerializedName("cast") val cast: List<Cast>,
    @SerializedName("crew") val crew: List<Crew>,
    @SerializedName("id") val id: Int
)