package com.akcay.justwatch.screens.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MovieRepository,
) : ViewModel() {
    val id = savedStateHandle.toRoute<MainDestination.MovieDetail>().id

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.onStart {
        getMovieDetailById()
        getMovieCastById()
        getMovieVideoById()
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = MovieDetailUiState())

    fun getMovieDetailById() {
        viewModelScope.launch {
            showLoading()
            when (val response = repo.getMovieById(movieId = id)) {
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
            hideLoading()
        }
    }

    fun getMovieCastById() {
        viewModelScope.launch {
            showLoading()
            when (val response = repo.getMovieCastById(movieId = id)) {
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
            hideLoading()
        }
    }

    fun getMovieVideoById() {
        viewModelScope.launch {
            showLoading()
            when (val response = repo.getMovieVideoById(movieId = id)) {
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
            hideLoading()
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
