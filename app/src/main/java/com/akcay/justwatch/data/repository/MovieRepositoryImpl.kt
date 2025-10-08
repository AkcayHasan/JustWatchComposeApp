package com.akcay.justwatch.data.repository

import com.akcay.justwatch.data.remote.datasource.MovieRemoteDataSource
import com.akcay.justwatch.domain.mapper.toDomainModel
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.domain.model.MovieCredits
import com.akcay.justwatch.domain.model.MovieDetail
import com.akcay.justwatch.domain.model.MovieVideos
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.util.map
import com.akcay.justwatch.screens.movies.domain.mapper.toUIModel
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
) : MovieRepository {

    override fun getAllPopularMovies(pageNumber: Int): Flow<NetworkResult<PageData<MovieUIModel>>> = flow {
        emit(
            remoteDataSource.getAllPopularMovies(pageNumber).map { pageData ->
                PageData(
                    data = pageData.data.map { it.toUIModel() },
                    page = pageData.page,
                    totalPages = pageData.totalPages,
                )
            }
        )
    }

    override fun getTopRatedMovies(pageNumber: Int): Flow<NetworkResult<PageData<MovieUIModel>>> = flow {
        emit(
            remoteDataSource.getTopRatedMovies(pageNumber).map { pageData ->
                PageData(
                    data = pageData.data.map { it.toUIModel() },
                    page = pageData.page,
                    totalPages = pageData.totalPages,
                )
            }
        )
    }

    override suspend fun getMovieById(movieId: Long): NetworkResult<MovieDetail> {
        return remoteDataSource.getMovieById(movieId).map { it.toDomainModel() }
    }

    override suspend fun getMovieCastById(movieId: Long): NetworkResult<MovieCredits> {
        return remoteDataSource.getMovieCredits(id = movieId).map { it.toDomainModel() }
    }

    override suspend fun getMovieVideoById(movieId: Long): NetworkResult<MovieVideos> {
        return remoteDataSource.getMovieVideo(id = movieId).map { it.toDomainModel() }
    }
}