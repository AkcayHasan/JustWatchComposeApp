package com.akcay.justwatch.internal.paging

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
class PageData<T>(
    @SerialName("results") val data: List<T>,
    @SerialName("page") val page: Int,
    @SerialName("total_pages") val totalPages: Int
)
