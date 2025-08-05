package com.akcay.justwatch.tests.viewmodel

import app.cash.turbine.test
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.screens.login.LoginScreenViewModelEvent
import com.akcay.justwatch.screens.login.LoginViewModel
import com.akcay.justwatch.tests.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelUnitTest {

    private val logRepository = mockk<LogRepository>(relaxed = true)
    private val dataStoreManager = mockk<DataStoreManager>(relaxed = true)
    private val signInUseCase = mockk<SignInUseCase>()

    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(
            logRepository = logRepository,
            dataStoreManager = dataStoreManager,
            signInUseCase = signInUseCase,
        )
    }

    @Test
    fun `checkRememberedSection sets email password and checkbox if values exist`() = runTest(dispatcherRule.dispatcher) {
        coEvery { dataStoreManager.getRememberedEmail() } returns "test@example.com"
        coEvery { dataStoreManager.getRememberedPassword() } returns "123456"
        coEvery { dataStoreManager.getRememberMeCheckboxStatus() } returns true

        viewModel = LoginViewModel(logRepository, dataStoreManager, signInUseCase)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("test@example.com", state.email)
        assertEquals("123456", state.password)
        assertTrue(state.isRememberCheckboxChecked)
        assertFalse(state.loading)
    }

    @Test
    fun `onLoginClick saves credentials and sends navigation event when login success`() = runTest(dispatcherRule.dispatcher) {
        // Given
        viewModel.onEmailChange("user@example.com")
        viewModel.onPasswordChange("secret")
        viewModel.setRememberMeChecked(true)

        coEvery { signInUseCase(any(), any()) } returns NetworkResult.Success(AuthUser())

        viewModel.channel.test {
            viewModel.onLoginClick()
            assertEquals(LoginScreenViewModelEvent.NavigateMoviesScreen, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            dataStoreManager.saveRememberedEmail("user@example.com")
            dataStoreManager.saveRememberedPassword("secret")
            dataStoreManager.saveRememberMeCheckboxStatus(true)
        }

        assertFalse(viewModel.uiState.value.loading)
    }

    @Test
    fun `onLoginClick clears remembered values if checkbox is false`() = runTest(dispatcherRule.dispatcher) {
        // Given
        viewModel.onEmailChange("user@example.com")
        viewModel.onPasswordChange("secret")
        viewModel.setRememberMeChecked(false)

        coEvery { signInUseCase(any(), any()) } returns NetworkResult.Success(AuthUser())

        viewModel.channel.test {
            viewModel.onLoginClick()
            assertEquals(LoginScreenViewModelEvent.NavigateMoviesScreen, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            dataStoreManager.clearRememberedEmail()
            dataStoreManager.clearRememberedPassword()
            dataStoreManager.saveRememberMeCheckboxStatus(false)
        }

        assertFalse(viewModel.uiState.value.loading)
    }
}
