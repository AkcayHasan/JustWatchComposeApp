package com.akcay.justwatch.internal.paging

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class PageData<T>(
    @SerializedName("results") val data: List<T>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)
