package com.akcay.justwatch.data.remote.datasource

import com.akcay.justwatch.data.remote.api.MovieService
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) {


}