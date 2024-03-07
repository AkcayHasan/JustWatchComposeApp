package com.akcay.justwatch.data.model.detailresponse

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("name") val name: String
)