package com.akcay.justwatch.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
  private val repo: MovieRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(MovieDetailUiState())
  val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

  fun getMovieDetailById(movieId: Long) {
    viewModelScope.launch {
      showLoading()
      when (val response = repo.getMovieById(movieId = movieId)) {
        is NetworkResult.Success -> {
          _uiState.update { _uiState.value.copy(movieDetail = response.data) }
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

  fun getMovieCastById(movieId: Long) {
    viewModelScope.launch {
      when (val response = repo.getMovieCastById(movieId = movieId)) {
        is NetworkResult.Success -> {
          _uiState.update { _uiState.value.copy(movieCast = response.data) }
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

  fun getMovieVideoById(movieId: Long) {
    viewModelScope.launch {
      when (val response = repo.getMovieVideoById(movieId = movieId)) {
        is NetworkResult.Success -> {
          _uiState.update { _uiState.value.copy(movieTrailers = response.data) }
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

  fun clearState() {
    _uiState.update {
      _uiState.value.copy(movieDetail = null, movieCast = null, movieTrailers = null)
    }
  }

  fun showLoading() {
    _uiState.update {
      _uiState.value.copy(loadingState = true)
    }
  }

  fun hideLoading() {
    _uiState.update {
      _uiState.value.copy(loadingState = false)
    }
  }
}