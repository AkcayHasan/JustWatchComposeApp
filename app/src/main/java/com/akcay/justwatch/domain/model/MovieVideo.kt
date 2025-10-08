package com.akcay.justwatch.domain.model

data class MovieVideo(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val publishedAt: String
)

data class MovieVideos(
    val videos: List<MovieVideo>
)
