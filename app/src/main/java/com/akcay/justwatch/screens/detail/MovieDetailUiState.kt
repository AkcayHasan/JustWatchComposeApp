package com.akcay.justwatch.screens.detail

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.detailresponse.videoresponse.MovieVideoResponse

data class MovieDetailUiState(
  val loadingState: Boolean = false,
  val movieDetail: MovieDetailResponse? = null,
  val movieCast: MovieDetailCreditsResponse? = null,
  val movieTrailers: MovieVideoResponse? = null
  )