package com.akcay.justwatch.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.domain.usecase.GetAllPopularMoviesUseCase
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.usecase.GetUserInfoUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.util.JWLoadingManager
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
  private val loadingState: JWLoadingManager,
  private val logRepository: LogRepository,
  private val getAllPopularMoviesUseCase: GetAllPopularMoviesUseCase,
  private val getUserInfoUseCase: GetUserInfoUseCase,
  accountRepository: AccountRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(MoviesUiState())
  val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

  private val _selectedIndex = MutableStateFlow(0)
  val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

  fun setSelectedIndex(index: Int) {
    _selectedIndex.value = index
  }

  init {
    viewModelScope.launch {
      val userJob = async {
        accountRepository.currentAuthUser.collect { authUser ->
          if (authUser != null) {
            getUserInfo(authUser.id!!)
          }
        }
      }
      val movieJob = async { getAllPopularMovies() }

      userJob.await()
      movieJob.await()
    }
  }

  private fun getUserInfo(uid: String) {
    launchCatching(logRepository = logRepository) {
      loadingState.showLoading()
      when (val result = getUserInfoUseCase(uid)) {
        is NetworkResult.Success -> {
          loadingState.hideLoading()
          _uiState.update {
            _uiState.value.copy(
              user = User(
                firstName = result.data.firstName,
                lastName = result.data.lastName
              )
            )
          }
        }

        is NetworkResult.Error -> {
          loadingState.hideLoading()
        }

        is NetworkResult.Exception -> {
          loadingState.hideLoading()

        }
      }
    }
  }

  private fun getAllPopularMovies() {
    launchCatching(logRepository = logRepository) {
      loadingState.showLoading()
      try {
        getAllPopularMoviesUseCase().collect { result ->
          when (result) {
            is NetworkResult.Success -> {
              _uiState.update { _uiState.value.copy(movieList = result.data) }
            }

            is NetworkResult.Error -> {

            }

            is NetworkResult.Exception -> {

            }
          }
        }
      } finally {
        loadingState.hideLoading()
      }
    }
  }
}