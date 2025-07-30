package com.akcay.justwatch.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.internal.navigation.AppDestination
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.JWSecurityUtil
import com.akcay.justwatch.internal.util.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    storeManager: DataStoreManager,
    private val themeManager: ThemeManager,
) : ViewModel() {
    private val _isRooted = MutableStateFlow(false)
    val isRooted = _isRooted.asStateFlow()

    val navigationDestination = combine(
        storeManager.shouldOnBoardingVisible(),
        storeManager.getDarkThemeEnabled()
    ) { onBoardingVisibility, darkThemEnabled ->
        themeManager.setDarkThemeEnabled(darkThemEnabled)
        when {
            onBoardingVisibility -> AppDestination.OnBoarding
            else -> AppDestination.Login
        }
    }.onStart {
        _isRooted.value = JWSecurityUtil.isDeviceRooted()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
}
