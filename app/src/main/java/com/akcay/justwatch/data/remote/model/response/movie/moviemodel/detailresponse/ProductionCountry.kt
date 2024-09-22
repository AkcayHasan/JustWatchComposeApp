package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso_3166_1: String,
    @SerializedName("name") val name: String
)