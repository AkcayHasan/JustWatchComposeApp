package com.akcay.justwatch.screens.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.model.detailresponse.MovieDetailResponse
import com.akcay.justwatch.data.model.detailresponse.creditsresponse.MovieDetailCreditsResponse
import com.akcay.justwatch.data.model.detailresponse.videoresponse.MovieVideoResponse
import com.akcay.justwatch.repository.MovieRepository
import com.akcay.justwatch.util.Constants
import com.akcay.justwatch.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MovieRepository
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>(MOVIE_ID_SAVED_STATE_KEY) ?: Constants.ZERO

    private val _loadingState = MutableStateFlow(value = false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _movieById = MutableStateFlow<MovieDetailResponse?>(value = null)
    val movieById: StateFlow<MovieDetailResponse?> = _movieById

    private val _castById = MutableStateFlow<MovieDetailCreditsResponse?>(value = null)
    val castById: StateFlow<MovieDetailCreditsResponse?> = _castById

    private val _videoById = MutableStateFlow<MovieVideoResponse?>(value = null)
    val videoById: StateFlow<MovieVideoResponse?> = _videoById

    init {
        getMovieDetailById(movieId)
        getMovieCastById(movieId)
        getMovieVideoById(movieId)
    }

    private fun getMovieDetailById(movieId: Int) {
        viewModelScope.launch {
            showLoading()
            try {
                when (val response = repo.getMovieById(movieId = movieId)) {
                    is NetworkResult.Success -> {
                        _movieById.value = response.data
                        Log.d("osman", "success: ${response.data}")
                    }

                    is NetworkResult.Error -> {
                        Log.d("osman", "error: ${response.message}")
                    }

                    is NetworkResult.Exception -> {
                        Log.d("osman", "getMovieById: ${response.e}")
                    }
                }
            } finally {
                hideLoading()
            }
        }
    }

    private fun getMovieCastById(movieId: Int) {
        viewModelScope.launch {
            showLoading()
            when (val response = repo.getMovieCastById(movieId = movieId)) {
                is NetworkResult.Success -> {
                    hideLoading()
                    _castById.value = response.data
                    Log.d("osman", "success: ${response.data}")
                }

                is NetworkResult.Error -> {
                    hideLoading()
                    Log.d("osman", "error: ${response.message}")
                }

                is NetworkResult.Exception -> {
                    hideLoading()
                    Log.d("osman", "getMovieById: ${response.e}")
                }
            }
        }
    }

    private fun getMovieVideoById(movieId: Int) {
        viewModelScope.launch {
            when (val response = repo.getMovieVideoById(movieId = movieId)) {
                is NetworkResult.Success -> {
                    _videoById.value = response.data
                    Log.d("osman", "success: ${response.data}")
                }

                is NetworkResult.Error -> {
                    Log.d("osman", "error: ${response.message}")
                }

                is NetworkResult.Exception -> {
                    Log.d("osman", "getMovieById: ${response.e}")
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

    companion object {
        private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"
    }
}