package com.akcay.justwatch.tests.viewmodel

import com.akcay.justwatch.screens.register.RegisterViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {

        viewModel = RegisterViewModel()
    }

    @Test
    fun `onEmailChange updates uiState email`() {
        viewModel.onEmailChange("test@example.com")
        assertEquals("test@example.com", viewModel.uiState.value.email)
    }

    @Test
    fun `onPasswordChange updates uiState password`() {
        viewModel.onPasswordChange("123456")
        assertEquals("123456", viewModel.uiState.value.password)
    }

    @Test
    fun `onRepeatPasswordChange updates uiState repeatPassword`() {
        viewModel.onRepeatPasswordChange("123456")
        assertEquals("123456", viewModel.uiState.value.repeatPassword)
    }
}
