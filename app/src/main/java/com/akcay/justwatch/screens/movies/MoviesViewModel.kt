package com.akcay.justwatch.screens.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.domain.usecase.GetAllPopularMoviesUseCase
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.JWLoadingManager
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
  private val loadingState: JWLoadingManager,
  private val getAllPopularMoviesUseCase: GetAllPopularMoviesUseCase,
  accountRepository: AccountRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(MoviesUiState())

  private val _popularMovieList = MutableStateFlow<MovieResponse?>(value = null)
  val popularMovieList: StateFlow<MovieResponse?> = _popularMovieList.asStateFlow()

  val uiState: StateFlow<MoviesUiState> = accountRepository.currentAuthUser
    .map { authUser ->
      _uiState.update { it.copy(currentUser = authUser) }
      _uiState.value
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = MoviesUiState()
    )

  fun getAllPopularMovies() {
    viewModelScope.launch {
      loadingState.showLoading()
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
        loadingState.hideLoading()
      }
    }
  }
}