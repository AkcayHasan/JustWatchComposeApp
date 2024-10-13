package com.akcay.justwatch.screens.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.domain.usecase.GetAllPopularMoviesUseCase
import com.akcay.justwatch.firebase.service.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
  private val getAllPopularMoviesUseCase: GetAllPopularMoviesUseCase,
  accountRepository: AccountRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(MoviesUiState())

  val uiState: StateFlow<MoviesUiState> = accountRepository.currentAuthUser.map {
    MoviesUiState(currentUser = it)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = MoviesUiState()
  )

  private val _popularMovieList = MutableStateFlow<MovieResponse?>(value = null)
  val popularMovieList: StateFlow<MovieResponse?> = _popularMovieList

  fun getAllPopularMovies() {
    viewModelScope.launch {
      showLoading()
      try {
        getAllPopularMoviesUseCase().collect {
          when (it) {
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
        }
      } finally {
        hideLoading()
      }
    }
  }

  private fun showLoading() {
    _uiState.value = uiState.value.copy(isLoading = true)
  }

  private fun hideLoading() {
    _uiState.value = uiState.value.copy(isLoading = false)
  }
}