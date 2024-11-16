package com.akcay.justwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.internal.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JustWatchViewModel @Inject constructor(
    private val storeManager: DataStoreManager
) : ViewModel() {

    private val _startDestination = MutableStateFlow(MainDestinations.LOGIN_ROUTE)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            val shouldShowOnboarding = storeManager.shouldOnBoardingVisible()
            _startDestination.value = if (shouldShowOnboarding) {
                MainDestinations.ONBOARDING_ROUTE
            } else {
                MainDestinations.LOGIN_ROUTE
            }
        }
    }
}