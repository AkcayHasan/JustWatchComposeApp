package com.akcay.justwatch.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.usecase.CreateAnonymousUseCase
import com.akcay.justwatch.domain.usecase.RegisterUseCase
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.NetworkResult
import com.akcay.justwatch.internal.util.ValidationUtil
import com.akcay.justwatch.internal.util.FieldValidationState
import com.akcay.justwatch.internal.util.FormValidationState
import com.akcay.justwatch.internal.util.FieldKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logRepository: LogRepository,
    private val dataStoreManager: DataStoreManager,
    private val signInUseCase: SignInUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.onStart { checkRememberedSection() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LoginUiState(),
    )

    private val _channel = Channel<LoginScreenViewModelEvent>()
    val channel = _channel.receiveAsFlow()

    fun sendEvent(event: LoginScreenViewEvent) {
        when (event) {
            LoginScreenViewEvent.OnLoginClicked -> onLoginClick()
        }
    }

    private fun checkRememberedSection() {
        showLoading()
        viewModelScope.launch {
            dataStoreManager.apply {
                if (!getRememberedPassword().isNullOrEmpty() && !getRememberedEmail().isNullOrEmpty() && getRememberMeCheckboxStatus()) {
                    _uiState.update {
                        _uiState.value.copy(
                            email = getRememberedEmail().orEmpty(),
                            password = getRememberedPassword().orEmpty(),
                            isRememberCheckboxChecked = true,
                        )
                    }
                }
                hideLoading()
            }
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

    fun setRememberMeChecked(isChecked: Boolean) {
        _uiState.update {
            it.copy(
                isRememberCheckboxChecked = isChecked,
            )
        }
    }

    fun onLoginClick() {
        if (validateForm()) {
            launchCatching(logRepository) {
                showLoading()
                try {
                    when (signInUseCase(uiState.value.email, uiState.value.password)) {
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
                    if (isRememberCheckboxChecked) {
                        saveRememberedEmail(email)
                        saveRememberedPassword(password)
                    } else {
                        clearRememberedEmail()
                        clearRememberedPassword()
                    }
                    saveRememberMeCheckboxStatus(isRememberCheckboxChecked)
                }
                _channel.send(LoginScreenViewModelEvent.NavigateMoviesScreen)
            }
        }
    }

    private fun validateForm(): Boolean {
        val emailValidation = ValidationUtil.validateEmail(uiState.value.email)
        val passwordValidation = ValidationUtil.validatePassword(uiState.value.password)
        
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
        
        val isValid = emailValidation.isValid && passwordValidation.isValid
        
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
