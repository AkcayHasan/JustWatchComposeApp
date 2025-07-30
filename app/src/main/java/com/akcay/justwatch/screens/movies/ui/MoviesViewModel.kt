package com.akcay.justwatch.screens.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.domain.usecase.GetUserInfoUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.paging.Pager
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val logRepository: LogRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val accountRepository: AccountRepository,
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val pager = Pager()
    private val data = mutableMapOf<Int, PageData<MovieUIModel>>()
    private val _uiState = MutableStateFlow(MoviesUiState())

    val uiState = _uiState.onStart {
        fetchUser()
        loadMore()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, MoviesUiState())

    private fun fetchUser() {
        viewModelScope.launch {
            accountRepository.currentAuthUser.collect { authUser ->
                if (authUser != null) {
                    getUserInfo(authUser.id!!)
                }
            }
        }
    }

    private fun getUserInfo(uid: String) {
        launchCatching(logRepository = logRepository) {
            showLoading()
            when (val result = getUserInfoUseCase(uid)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            user = User(
                                firstName = result.data.firstName,
                                lastName = result.data.lastName,
                            ),
                        )
                    }
                }

                is NetworkResult.Error -> {
                }

                is NetworkResult.Exception -> {
                }
            }
            hideLoading()
        }
    }

    private var loadJob: Job? = null
    fun loadMore() {
        if (loadJob != null && pager.hasNextPage.not()) return

        loadJob = viewModelScope.launch {
            val currentPage = pager.currentPage
            movieRepository.getAllPopularMovies(pageNumber = currentPage).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {

                    }

                    is NetworkResult.Exception -> {

                    }

                    is NetworkResult.Success -> {
                        pager.currentPage += 1
                        pager.hasNextPage = result.data.data.size == pager.pageSize
                        data[pager.currentPage] = result.data
                        _uiState.update {
                            it.copy(
                                movieList = data.values.flatMap { list -> list.data },
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAddIconClicked(id: Long) {

    }

    private fun showLoading() {
        _uiState.update {
            it.copy(
                loading = true,
            )
        }
    }

    private fun hideLoading() {
        _uiState.update {
            it.copy(
                loading = false,
            )
        }
    }
}
