package com.akcay.justwatch.tests.viewmodel

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.repository.MovieRepository
import com.akcay.justwatch.domain.usecase.GetUserInfoUseCase
import com.akcay.justwatch.internal.paging.PageData
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.screens.movies.domain.model.MovieUIModel
import com.akcay.justwatch.screens.movies.ui.MoviesViewModel
import com.akcay.justwatch.tests.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelUnitTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val logRepository = mockk<LogRepository>(relaxed = true)
    private val getUserInfoUseCase = mockk<GetUserInfoUseCase>()
    private val accountRepository = mockk<AccountRepository>()
    private val movieRepository = mockk<MovieRepository>(relaxed = true)

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        coEvery { accountRepository.currentAuthUser } returns flowOf(null)

        viewModel = MoviesViewModel(
            logRepository = logRepository,
            getUserInfoUseCase = getUserInfoUseCase,
            accountRepository = accountRepository,
            movieRepository = movieRepository,
        )
    }

    @Test
    fun `fetchUser emits user info when account exists`() = runTest(dispatcherRule.dispatcher) {
        val fakeAuthUser = AuthUser(id = "uid123")
        val fakeUserInfo = User(firstName = "Hasan", lastName = "Akçay")

        coEvery { accountRepository.currentAuthUser } returns flowOf(fakeAuthUser)
        coEvery { getUserInfoUseCase.invoke("uid123") } returns NetworkResult.Success(fakeUserInfo)

        viewModel = MoviesViewModel(
            logRepository,
            getUserInfoUseCase,
            accountRepository,
            movieRepository,
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals("Hasan", state.user?.firstName)
        assertEquals("Akçay", state.user?.lastName)
        assertFalse(state.loading)
    }

//    @Test
//    fun `loadMore updates movieList and paging`() = runTest(dispatcherRule.dispatcher) {
//        var page = 1
//        val movie1 = MovieUIModel(id = 1, title = "Batman")
//        val movie2 = MovieUIModel(id = 2, title = "Superman")
//
//        val pageData = PageData(
//            data = listOf(movie1, movie2),
//            page = 1,
//            totalPages = 2,
//        )
//
//        coEvery { movieRepository.getAllPopularMovies(pageNumber = any()) } returns flowOf(
//            NetworkResult.Success(
//                pageData,
//            ),
//        )
//
//        viewModel.loadMore()
//        viewModel.loadMore()
//
//        advanceUntilIdle()
//
//        val state = viewModel.uiState.value
//
//        assertEquals(listOf(movie1, movie2, movie1, movie2), state.movieList)
//    }
}
