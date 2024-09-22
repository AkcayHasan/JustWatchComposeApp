package com.akcay.justwatch.screens.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.domain.usecase.GetAllPopularMoviesUseCase
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getAllPopularMoviesUseCase: GetAllPopularMoviesUseCase
): ViewModel() {

    private val _loadingState = MutableStateFlow(value = false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _popularMovieList = MutableStateFlow<MovieResponse?>(value = null)
    val popularMovieList: StateFlow<MovieResponse?> = _popularMovieList

    fun getAllPopularMovies() {
        viewModelScope.launch {
            showLoading()
            getAllPopularMoviesUseCase().collect {
                when(it) {
                    is NetworkResult.Success -> {
                        _popularMovieList.value = it.data
                        Log.d("osman", "success: ${it.data}")
                    }
                    is NetworkResult.Error -> {
                        Log.d("osman", "error: ${it.message}")
                    }
                    is NetworkResult.Exception -> {
                        Log.d("osman", "getAllPopularMovies: ${it.e}")
                    }
                }
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        _loadingState.value = true
    }

    private fun hideLoading() {
        _loadingState.value = false
    }
}