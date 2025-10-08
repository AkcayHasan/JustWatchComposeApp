package com.akcay.justwatch.domain.mapper

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.domain.model.MovieVideos
import com.akcay.justwatch.domain.model.MovieVideo

fun MovieVideoResponse.toDomainModel(): MovieVideos {
    return MovieVideos(
        videos = this.results.map { video ->
            MovieVideo(
                id = video.id,
                key = video.key,
                name = video.name,
                site = video.site,
                type = video.type,
                official = video.official,
                publishedAt = video.publishedAt
            )
        }
    )
}
