package com.akcay.justwatch.tests.viewmodel

import com.akcay.justwatch.firebase.service.AccountRepository
import com.akcay.justwatch.firebase.service.LogRepository
import com.akcay.justwatch.screens.register.RegisterViewModel
import com.akcay.justwatch.tests.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class RegisterViewModelUnitTest {

  private lateinit var registerViewModel: RegisterViewModel
  private lateinit var mockLogRepository: LogRepository
  private lateinit var mockAccountRepository: AccountRepository

  @get:Rule
  val mainDispatcher = MainDispatcherRule()

  @Before
  fun setup() {
    mockLogRepository = mock(LogRepository::class.java)
    mockAccountRepository = mock(AccountRepository::class.java)
    registerViewModel = RegisterViewModel(mockLogRepository, mockAccountRepository)
  }

  @Test
  fun `test email change updates uisState`() = runTest {
    val newEmail = "test@example.com"
    registerViewModel.onEmailChange(newEmail)

    assertEquals(newEmail, registerViewModel.uiState.value.email)
  }
}