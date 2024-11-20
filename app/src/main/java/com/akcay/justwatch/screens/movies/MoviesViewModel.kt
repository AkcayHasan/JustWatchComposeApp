package com.akcay.justwatch.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.usecase.GetPagedPopularMoviesUseCase
import com.akcay.justwatch.domain.usecase.GetUserInfoUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
  private val logRepository: LogRepository,
  getPagedPopularMoviesUseCase: GetPagedPopularMoviesUseCase,
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
      accountRepository.currentAuthUser.collect { authUser ->
        if (authUser != null) {
          getUserInfo(authUser.id!!)
        }
      }
    }
  }

  val popularMovies: Flow<PagingData<MovieResult>> =
    getPagedPopularMoviesUseCase()
      .cachedIn(viewModelScope)

  private fun getUserInfo(uid: String) {
    launchCatching(logRepository = logRepository) {
      showLoading()
      try {
        when (val result = getUserInfoUseCase(uid)) {
          is NetworkResult.Success -> {
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
          }

          is NetworkResult.Exception -> {
          }
        }
      } finally {
        hideLoading()
      }
    }
  }

  private fun showLoading() {
    _uiState.update {
      _uiState.value.copy(
        loading = true
      )
    }
  }

  private fun hideLoading() {
    _uiState.update {
      _uiState.value.copy(
        loading = false
      )
    }
  }
}