package com.akcay.justwatch.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.domain.usecase.RegisterUseCase
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.util.ValidationUtil
import com.akcay.justwatch.internal.util.FieldValidationState
import com.akcay.justwatch.internal.util.FormValidationState
import com.akcay.justwatch.internal.util.FieldKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class RegisterViewModel(
    private val accountRepository: AccountRepository,
    private val dataStoreManager: DataStoreManager,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.onStart { }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = RegisterUiState(),
    )

    private val _channel = Channel<RegisterScreenViewModelEvent>()
    val channel = _channel.receiveAsFlow()

    fun sendEvent(event: RegisterScreenViewEvent) {
        when (event) {
            RegisterScreenViewEvent.OnRegisterClicked -> onRegisterClick()
        }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update {
            it.copy(email = newValue)
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(password = newValue)
        }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(confirmPassword = newValue)
        }
    }

    fun onNameChange(newValue: String) {
        _uiState.update {
            it.copy(name = newValue)
        }
    }

    fun onSurnameChange(newValue: String) {
        _uiState.update {
            it.copy(surname = newValue)
        }
    }

    fun onRegisterClick() {
        if (validateForm()) {
            viewModelScope.launch {
                showLoading()
                try {
                    when (registerUseCase.invoke(
                        email = uiState.value.email,
                        password = uiState.value.password,
                        name = uiState.value.name,
                        surname = uiState.value.surname
                    )) {
                        is NetworkResult.Success -> {
                            loginUser()
                        }
                        else -> {}
                    }
                } finally {
                    hideLoading()
                }
            }
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            with(uiState.value) {
                dataStoreManager.apply {
                    saveRememberedEmail(email)
                    saveRememberedPassword(password)
                    saveRememberMeCheckboxStatus(true)
                }
                _channel.send(RegisterScreenViewModelEvent.NavigateMoviesScreen)
            }
        }
    }

    private fun validateForm(): Boolean {
        val emailValidation = ValidationUtil.validateEmail(uiState.value.email)
        val passwordValidation = ValidationUtil.validatePassword(uiState.value.password)
        val confirmPasswordValidation = ValidationUtil.validatePassword(uiState.value.confirmPassword)
        val nameValidation = ValidationUtil.validateName(uiState.value.name)
        val surnameValidation = ValidationUtil.validateSurname(uiState.value.surname)
        
        val fields = mutableMapOf<FieldKey, FieldValidationState>()
        
        if (!emailValidation.isValid) {
            fields[FieldKey.EMAIL] = FieldValidationState(
                hasError = true,
                errorMessage = emailValidation.errorMessage
            )
        }
        
        if (!passwordValidation.isValid) {
            fields[FieldKey.PASSWORD] = FieldValidationState(
                hasError = true,
                errorMessage = passwordValidation.errorMessage
            )
        }
        
        if (!confirmPasswordValidation.isValid) {
            fields[FieldKey.CONFIRM_PASSWORD] = FieldValidationState(
                hasError = true,
                errorMessage = confirmPasswordValidation.errorMessage
            )
        } else if (uiState.value.password != uiState.value.confirmPassword) {
            fields[FieldKey.CONFIRM_PASSWORD] = FieldValidationState(
                hasError = true,
                errorMessage = "Passwords do not match"
            )
        }
        
        if (!nameValidation.isValid) {
            fields[FieldKey.NAME] = FieldValidationState(
                hasError = true,
                errorMessage = nameValidation.errorMessage
            )
        }
        
        if (!surnameValidation.isValid) {
            fields[FieldKey.SURNAME] = FieldValidationState(
                hasError = true,
                errorMessage = surnameValidation.errorMessage
            )
        }
        
        val isValid = emailValidation.isValid && 
                     passwordValidation.isValid && 
                     confirmPasswordValidation.isValid &&
                     nameValidation.isValid &&
                     surnameValidation.isValid &&
                     uiState.value.password == uiState.value.confirmPassword
        
        _uiState.update { 
            it.copy(
                validationState = FormValidationState(
                    fields = fields,
                    showDialog = !isValid,
                    dialogMessage = if (!isValid) "Please fill in all required fields correctly." else null
                )
            )
        }
        
        return isValid
    }
    
    fun clearValidationErrors() {
        _uiState.update { 
            it.copy(
                validationState = FormValidationState()
            )
        }
    }
    
    fun dismissDialog() {
        _uiState.update { 
            it.copy(
                validationState = it.validationState.copy(showDialog = false)
            )
        }
    }

    private fun showLoading() = _uiState.update { it.copy(loading = true) }
    private fun hideLoading() = _uiState.update { it.copy(loading = false) }
}
