package com.akcay.justwatch.domain.usecase

import androidx.paging.PagingData
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.internal.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedPopularMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<MovieResult>> {
        return repo.getPagedPopularMovies()
    }
}