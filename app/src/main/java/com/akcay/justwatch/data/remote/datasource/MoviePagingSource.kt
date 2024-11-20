package com.akcay.justwatch.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class MoviePagingSource(
  private val remoteDataSource: MovieRemoteDataSource,
  private val dispatcher: CoroutineDispatcher
) : PagingSource<Int, MovieResult>() {

  override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
    return state.anchorPosition?.let { anchor ->
      val closestPage = state.closestPageToPosition(anchor)
      closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
    val currentPage = params.key ?: 1

    return try {
      val response = safeApiCall(dispatcher) {
        remoteDataSource.getAllPopularMovies(currentPage)
      }

      when (response) {
        is NetworkResult.Success -> {
          val movies = response.data.movieResults
          val nextKey = if (movies?.isEmpty() == true) null else currentPage + 1

          LoadResult.Page(
            data = movies!!,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = nextKey
          )
        }
        is NetworkResult.Error -> {
          LoadResult.Error(Exception(response.message))
        }
        is NetworkResult.Exception -> {
          LoadResult.Error(Exception("Loading state unsupported in PagingSource"))
        }
      }
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }
}