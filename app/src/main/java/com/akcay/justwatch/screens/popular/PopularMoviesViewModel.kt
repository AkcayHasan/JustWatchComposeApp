package com.akcay.justwatch.screens.popular

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.model.listresponse.MovieResponse
import com.akcay.justwatch.repository.MovieRepository
import com.akcay.justwatch.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repo: MovieRepository
): ViewModel() {

    private val _loadingState = MutableStateFlow(value = false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _popularMovieList = MutableStateFlow<MovieResponse?>(value = null)
    val popularMovieList: StateFlow<MovieResponse?> = _popularMovieList

    fun getAllPopularMovies() {
        viewModelScope.launch {
            showLoading()
            repo.getAllPopularMovies().collect {
                when(it) {
                    is NetworkResult.Success -> {
                        hideLoading()
                        _popularMovieList.value = it.data
                        Log.d("osman", "success: ${it.data}")
                    }
                    is NetworkResult.Error -> {
                        hideLoading()
                        Log.d("osman", "error: ${it.message}")
                    }
                    is NetworkResult.Exception -> {
                        hideLoading()
                        Log.d("osman", "getAllPopularMovies: ${it.e}")
                    }
                }
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