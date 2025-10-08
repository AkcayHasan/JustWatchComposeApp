package com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Crew(
    @SerialName("adult") val adult: Boolean,
    @SerialName("credit_id") val creditId: String,
    @SerialName("department") val department: String,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("job") val job: String,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profilePath: String
)
