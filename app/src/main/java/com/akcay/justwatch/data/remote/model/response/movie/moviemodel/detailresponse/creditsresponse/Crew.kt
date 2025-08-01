package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("department") val department: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("job") val job: String,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profilePath: String
)
