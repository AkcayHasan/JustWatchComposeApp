package com.akcay.justwatch.screens.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.domain.usecase.GetUserInfoUseCase
import com.akcay.justwatch.internal.component.TabRowItem
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.paging.Pager
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
    private val popularPager = Pager()
    private val topRatedPager = Pager()
    private val popularData = mutableMapOf<Int, PageData<MovieUIModel>>()
    private val topRatedData = mutableMapOf<Int, PageData<MovieUIModel>>()
    private val _uiState = MutableStateFlow(MoviesUiState())

    val uiState = _uiState.onStart {
        fetchUser()
        loadMore()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, MoviesUiState())

    private fun fetchUser() {
        viewModelScope.launch {
            showLoading()
            accountRepository.currentAuthUser.collect { authUser ->
                if (authUser != null) {
                    getUserInfo(authUser.id!!)
                }
            }
            hideLoading()
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

    private var popularLoadJob: Job? = null
    private var topRatedLoadJob: Job? = null
    
    fun loadMore() {
        when (_uiState.value.selectedTab) {
            TabRowItem.ACTIVE -> loadPopularMovies()
            TabRowItem.TOP_RATED -> loadTopRatedMovies()
        }
    }
    
    private fun loadPopularMovies() {
        if (popularLoadJob != null && popularPager.hasNextPage.not()) return

        popularLoadJob = viewModelScope.launch {
            val currentPage = popularPager.currentPage
            movieRepository.getAllPopularMovies(pageNumber = currentPage).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {

                    }

                    is NetworkResult.Exception -> {

                    }

                    is NetworkResult.Success -> {
                        popularPager.currentPage += 1
                        popularPager.hasNextPage = result.data.data.size == popularPager.pageSize
                        popularData[popularPager.currentPage] = result.data
                        _uiState.update {
                            it.copy(
                                movieList = popularData.values
                                    .flatMap { list -> list.data }
                                    .distinctBy { movie -> movie.id },
                            )
                        }
                    }
                }
            }
        }
    }
    
    private fun loadTopRatedMovies() {
        if (topRatedLoadJob != null && topRatedPager.hasNextPage.not()) return

        topRatedLoadJob = viewModelScope.launch {
            val currentPage = topRatedPager.currentPage
            movieRepository.getTopRatedMovies(pageNumber = currentPage).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {

                    }

                    is NetworkResult.Exception -> {

                    }

                    is NetworkResult.Success -> {
                        topRatedPager.currentPage += 1
                        topRatedPager.hasNextPage = result.data.data.size == topRatedPager.pageSize
                        topRatedData[topRatedPager.currentPage] = result.data
                        _uiState.update {
                            it.copy(
                                topRatedMovieList = topRatedData.values
                                    .flatMap { list -> list.data }
                                    .distinctBy { movie -> movie.id },
                            )
                        }
                    }
                }
            }
        }
    }
    
    fun onTabChanged(tab: TabRowItem) {
        _uiState.update { it.copy(selectedTab = tab) }
        loadMore()
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
