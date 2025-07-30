package com.akcay.justwatch.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.LogRepository
import com.akcay.justwatch.domain.usecase.CreateAnonymousUseCase
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.internal.ext.launchCatching
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.NetworkResult
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
    private val createAnonymousUseCase: CreateAnonymousUseCase,
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
            LoginScreenViewEvent.OnGuestEntryClicked -> createAnonymousUser()
            LoginScreenViewEvent.OnLoginClicked -> onLoginClick()
            LoginScreenViewEvent.OnRegisterClicked -> {

            }
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
        launchCatching(logRepository) {
            showLoading()
            try {
                with(uiState.value) {
                    when (val result = signInUseCase(email, password)) {
                        is NetworkResult.Success -> {
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

                        is NetworkResult.Error -> {
                        }

                        is NetworkResult.Exception -> {

                        }
                    }
                }
            } finally {
                hideLoading()
            }
        }
    }

    private fun showLoading() = _uiState.update { it.copy(loading = true) }
    private fun hideLoading() = _uiState.update { it.copy(loading = false) }

    fun createAnonymousUser() {
        launchCatching(logRepository) {
            showLoading()
            createAnonymousUseCase.invoke()
            _channel.send(LoginScreenViewModelEvent.NavigateMoviesScreen)
        }
    }
}
